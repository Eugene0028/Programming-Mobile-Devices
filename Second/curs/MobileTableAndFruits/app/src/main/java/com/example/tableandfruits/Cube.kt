package com.example.tableandfruits

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer


class Cube {
    private val vertexBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer
    private val program: Int

    private val vertices = floatArrayOf(
        // Координаты вершин куба
        -0.5f, -0.5f, -0.5f,  // 0
        0.5f, -0.5f, -0.5f,  // 1
        0.5f,  0.5f, -0.5f,  // 2
        -0.5f,  0.5f, -0.5f,  // 3
        -0.5f, -0.5f,  0.5f,  // 4
        0.5f, -0.5f,  0.5f,  // 5
        0.5f,  0.5f,  0.5f,  // 6
        -0.5f,  0.5f,  0.5f   // 7
    )

    private val indices = shortArrayOf(
        0, 1, 2, 0, 2, 3, // Передняя грань
        4, 5, 6, 4, 6, 7, // Задняя грань
        0, 1, 5, 0, 5, 4, // Нижняя грань
        2, 3, 7, 2, 7, 6, // Верхняя грань
        0, 3, 7, 0, 7, 4, // Левая грань
        1, 2, 6, 1, 6, 5  // Правая грань
    )

    init {
        // Создаем буферы для вершин и индексов
        vertexBuffer = ByteBuffer.allocateDirect(vertices.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(vertices)
                position(0)
            }
        }

        indexBuffer = ByteBuffer.allocateDirect(indices.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(indices)
                position(0)
            }
        }

        program = loadShaders() // Загрузка шейдеров
    }

    private fun loadShaders(): Int {
        // Определите шейдеры здесь
        val vertexShaderCode = """
            attribute vec4 vPosition;
            void main() {
                gl_Position = vPosition;
            }
        """.trimIndent()

        val fragmentShaderCode = """
            precision mediump float;
            void main() {
                gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0); // Красный цвет
            }
        """.trimIndent()

        val vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER).also {
            GLES20.glShaderSource(it, vertexShaderCode)
            GLES20.glCompileShader(it)
        }

        val fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER).also {
            GLES20.glShaderSource(it, fragmentShaderCode)
            GLES20.glCompileShader(it)
        }

        return GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    fun draw() {
        GLES20.glUseProgram(program)

        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        vertexBuffer.position(0)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size, GLES20.GL_UNSIGNED_SHORT, indexBuffer)

        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}
