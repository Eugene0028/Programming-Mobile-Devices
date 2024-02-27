using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class PlayerController : MonoBehaviour
{

    private CharacterController controller;
    private Vector3 direction;
    public float forwardSpeed;
    private int desiredLine = 1;
    public float lineDistance = 4;
    public float jumpForce = 0.5f;
    public float gravity = -20;
    public Animator animator;
    public float maxSpeed;
    private float originalSpeed;
    private float speedMultiplier = 1f;

    // Start is called before the first frame update
    void Start()
    {
        originalSpeed = forwardSpeed;
        controller = GetComponent<CharacterController>();
        transform.position = new Vector3(0, -1.5f, -6.5f);
        controller.center = new Vector3(0, 0.9f, 0);
    }

    // Update is called once per frame
    void Update()
    {
        speedMultiplier = originalSpeed / forwardSpeed;
       
        if (!PlayerManager.isGameStarted) return;

        if (forwardSpeed < maxSpeed) forwardSpeed += 0.1f * Time.deltaTime;
    
     
        animator.SetBool("isGameStarted", true);


        direction.z = forwardSpeed;
        direction.y += gravity * Time.deltaTime;


        animator.SetBool("isGrounded", controller.isGrounded);

        
        if (SwipeManager.swipeUp && controller.isGrounded)
        {
            //animator.SetBool("isTurn", false);
            Jump();
        }

        if (SwipeManager.swipeDown)
        {
            StartCoroutine(Slide());
            //animator.SetBool("isSliding", false);
        }
      
        if (SwipeManager.swipeRight)
        {
            //animator.SetBool("isTurn", true);
            animator.SetBool("isGrounded", false);
            desiredLine++;
            if (desiredLine == 3) desiredLine = 2;
        }
        if (SwipeManager.swipeLeft)
        {
            //animator.SetBool("isTurn", true);
            animator.SetBool("isGrounded", false);
            desiredLine--;
            if (desiredLine == -1) desiredLine = 0;
        }

        Vector3 targetPosition = transform.position.z * transform.forward + transform.position.y * transform.up;
        
        if (desiredLine == 0)
        {
            targetPosition += Vector3.left * lineDistance;
        }
        else if (desiredLine == 2)
        {
            targetPosition += Vector3.right * lineDistance;
        }
        if (transform.position == targetPosition) { return; }
        Vector3 diff = targetPosition - transform.position; 
        Vector3 moveDir = diff.normalized *25 * Time.deltaTime;
        if (moveDir.sqrMagnitude < diff.sqrMagnitude) { controller.Move(moveDir); } 
        else { controller.Move(diff); }

    }
    private void FixedUpdate()
    {
        controller.Move(direction * Time.fixedDeltaTime);
    }

    private void Jump()
    {
        direction.y = jumpForce;
    }

    private void OnControllerColliderHit(ControllerColliderHit hit)
    {
        if (hit.transform.tag == "Obstacle")
        {
            PlayerManager.gameOver = true;
            FindObjectOfType<AudioManager>().PlaySound("GameOver");
        }
    }

    private IEnumerator Slide() {
        animator.SetBool("isSliding", true);
        controller.center = new Vector3(0, 0.5f, 0);
        controller.height = 1;



        yield return new WaitForSeconds(1.1f * speedMultiplier);
        controller.center = new Vector3(0, 0.9f, 0);
        controller.height = 2;
        animator.SetBool("isSliding", false);
    }
}
