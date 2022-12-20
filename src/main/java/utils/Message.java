/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author dina
 */
public class Message {
 Success data;
 Fail error;
 

    public Message() {
    }
 
public Message( Fail error){
            this.error = error;

}
    public Message(Success data) {
        this.data = data;
    }
    
}
