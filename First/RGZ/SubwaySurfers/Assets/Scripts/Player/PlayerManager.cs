using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerManager : MonoBehaviour
{
    public static bool gameOver;
    public GameObject gameOverPanel;
    public static bool isGameStarted;
    public GameObject startingText;
    public Text coinStatus;
    public Text timer;
    private float time;
    public static long numberOfCoins;
    void Start()
    {
        gameOver = false;
        isGameStarted = false;
        Time.timeScale = 1.0f;
        numberOfCoins = 0;
        timer.text = "Time: " + 0;
    }

    // Update is called once per frame
    void Update()
    {
        if (time.ToString("F2") == "60,00")
        {
            gameOver = true;
        }
        time += Time.deltaTime;
        coinStatus.text = "Coins: " + numberOfCoins;
        if (isGameStarted) timer.text = "Time: " + time.ToString("F2");
        if (gameOver)
        {
            Time.timeScale = 0;
            gameOverPanel.SetActive(true);
        }

        if (SwipeManager.tap)
        {
            isGameStarted = true;
            Destroy(startingText);
        }
    }
}
