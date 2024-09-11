using System.Collections;
using System.Collections.Generic;
using System.Data;
using UnityEngine;

public class NewBehaviourScript : MonoBehaviour
{
    public GameObject[] tilePrefabs;
    public float zSpawn = 0f;
    public float tileLength = 30;
    public int numberOfTiles = 5;
    private List<GameObject> activeTiles = new List<GameObject>();
    public Transform player;

    // Start is called before the first frame update
    void Start()
    {

        //for(int i = 0; i < numberOfTiles; i++) { SpawnTile(i); }
        SpawnTile(0);
        for (int i = 0; i < numberOfTiles; i++)
        {
            if (i == 0) SpawnTile(0);
            else SpawnTile(Random.Range(0, tilePrefabs.Length));
        }
    }

    // Update is called once per frame
    void Update()
    {
        if (player.transform.position.z - 35 > zSpawn-(numberOfTiles * tileLength))
        {
            SpawnTile(Random.Range(0, tilePrefabs.Length));
            DeleteTile();
        }
    }
    public void SpawnTile(int tileIndex)
    {

        GameObject jo = Instantiate(tilePrefabs[tileIndex], transform.forward * zSpawn, transform.rotation);
        activeTiles.Add(jo);
        zSpawn += tileLength;
    }
    private void DeleteTile()
    {
        Destroy(activeTiles[0]);
        activeTiles.RemoveAt(0);
    }
}
