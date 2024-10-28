package com.example.tableandfruits

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.math.cos
import kotlin.math.sin

class Candle(private val context: Context) {

    private val vertexBufferLiquid: FloatBuffer
    private val colorBufferLiquid: FloatBuffer
    private val normalBuffer: FloatBuffer

    private var program: Int
    private val modelMatrix = FloatArray(16)

    private val liquidVertices: FloatArray = generateCylinderVertices(0.06f, 0.3f, 30)
    private val liquidNormals: FloatArray = generateCylinderNormals(0.06f, 0.3f, 30)


    private val liquidColors: FloatArray = FloatArray(liquidVertices.size / 3 * 4).apply {
        for (i in indices step 4) {
            this[i] = 4.6f  // Красный компонент
            this[i + 1] = 5.3f  // Зеленый компонент
            this[i + 2] = 0.9f  // Синий компонент
            this[i + 3] = 7.9f
        }
    }

    init {
        // Инициализация буферов для жидкости
        vertexBufferLiquid = ByteBuffer.allocateDirect(liquidVertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexBufferLiquid.put(liquidVertices).position(0)

        colorBufferLiquid = ByteBuffer.allocateDirect(liquidColors.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        colorBufferLiquid.put(liquidColors).position(0)

        normalBuffer = ByteBuffer.allocateDirect(liquidNormals.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        normalBuffer.put(liquidNormals).position(0)

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        program = GLES20.glCreateProgram().apply {
            GLES20.glAttachShader(this, vertexShader)
            GLES20.glAttachShader(this, fragmentShader)
            GLES20.glLinkProgram(this)
        }
    }

    fun draw(mVPMatrix: FloatArray, lightPos: FloatArray, viewPos: FloatArray) {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        GLES20.glUseProgram(program)

        drawObject(vertexBufferLiquid, colorBufferLiquid, normalBuffer, mVPMatrix, lightPos, viewPos)

        GLES20.glDisable(GLES20.GL_BLEND)
    }

    private fun drawObject(vertexBuffer: FloatBuffer, colorBuffer: FloatBuffer, normalBuffer: FloatBuffer, mVPMatrix: FloatArray, lightPos: FloatArray, viewPos: FloatArray) {
        // Применение трансформаций
        Matrix.setIdentityM(modelMatrix, 0)
        val finalMatrix = FloatArray(16)
        Matrix.multiplyMM(finalMatrix, 0, mVPMatrix, 0, modelMatrix, 0)

        // Связка атрибутов вершин
        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer)

        // Связка атрибутов цветов
        val colorHandle = GLES20.glGetAttribLocation(program, "vColor")
        GLES20.glEnableVertexAttribArray(colorHandle)
        GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false, 16, colorBuffer)

        // Связка атрибутов нормалей
        val normalHandle = GLES20.glGetAttribLocation(program, "a_Normal")
        GLES20.glEnableVertexAttribArray(normalHandle)
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false, 12, normalBuffer)

        // Связка матрицы трансформации
        val matrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, finalMatrix, 0)

        // Связка для света и камеры
        val lightPosHandle = GLES20.glGetUniformLocation(program, "u_LightPos")
        val viewPosHandle = GLES20.glGetUniformLocation(program, "u_ViewPos")
        GLES20.glUniform3fv(lightPosHandle, 1, lightPos, 0)
        GLES20.glUniform3fv(viewPosHandle, 1, viewPos, 0)

        // Отрисовка цилиндра как треугольных полос
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexBuffer.limit() / 3)

        // Отключение атрибутов
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(colorHandle)
        GLES20.glDisableVertexAttribArray(normalHandle)
    }

    // Генерация вершин цилиндра
    private fun generateCylinderVertices(radius: Float, height: Float, segments: Int): FloatArray {
        val vertices = ArrayList<Float>()
        val angleStep = (2 * Math.PI / segments).toFloat()

        for (i in 0..segments) {
            val angle = i * angleStep
            val x = (radius * Math.cos(angle.toDouble())).toFloat()
            val z = (radius * Math.sin(angle.toDouble())).toFloat()

            // Верхнее основание
            vertices.add(x)
            vertices.add(height / 2)
            vertices.add(z)

            // Нижнее основание
            vertices.add(x)
            vertices.add(-height / 2)
            vertices.add(z)
        }

        return vertices.toFloatArray()
    }

    // Генерация нормалей цилиндра
    private fun generateCylinderNormals(radius: Float, height: Float, segments: Int): FloatArray {
        val normals = ArrayList<Float>()
        val angleStep = (2 * Math.PI / segments).toFloat()

        for (i in 0..segments) {
            val angle = i * angleStep
            val x = (radius * Math.cos(angle.toDouble())).toFloat()
            val z = (radius * Math.sin(angle.toDouble())).toFloat()

            // Нормали направлены наружу
            normals.add(x)
            normals.add(0f) // Нормаль по Y - цилиндр, поэтому 0
            normals.add(z)

            normals.add(x)
            normals.add(0f) // Нормаль по Y - цилиндр, поэтому 0
            normals.add(z)
        }

        return normals.toFloatArray()
    }

    // Компиляция шейдера
    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    companion object {
        // Вершинный шейдер
        private const val vertexShaderCode =
            """
            uniform mat4 uMVPMatrix;
            attribute vec4 vPosition;
            attribute vec4 vColor;
            attribute vec3 a_Normal;
            varying vec4 outColor;
            varying vec3 v_Normal;
            varying vec3 v_LightDir;
            varying vec3 v_ViewDir;

            void main() {
                gl_Position = uMVPMatrix * vPosition;
                outColor = vColor;

                // Нормали для освещения
                v_Normal = normalize(a_Normal);
                v_LightDir = normalize(vec3(1.0, 1.0, 1.0)); // Направление света (можно изменить)
                v_ViewDir = normalize(-vec3(gl_Position)); // Направление взгляда
            }
            """

        // Фрагментный шейдер
        private const val fragmentShaderCode =
            """
            precision mediump float;
            varying vec4 outColor;
            varying vec3 v_Normal;
            varying vec3 v_LightDir;
            varying vec3 v_ViewDir;

            void main() {
                // Основное освещение
                vec3 norm = normalize(v_Normal);
                vec3 lightDir = normalize(v_LightDir);
                vec3 viewDir = normalize(v_ViewDir);

                // Ambient Light
                vec3 ambient = 0.1 * outColor.rgb;

                // Diffuse Light
                float diff = max(dot(norm, lightDir), 0.0);
                vec3 diffuse = diff * outColor.rgb;

                // Specular Light
                vec3 reflectDir = reflect(-lightDir, norm);
                float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0);
                vec3 specular = spec * vec3(1.0); // Цвет блики

                // Финальный цвет
                vec3 finalColor = ambient + diffuse + specular;
                gl_FragColor = vec4(finalColor, outColor.a);
            }
            """
    }
}