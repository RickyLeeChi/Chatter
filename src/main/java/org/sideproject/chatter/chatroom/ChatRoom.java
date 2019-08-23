package org.sideproject.chatter.chatroom;

public class ChatRoom implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Inside : " + Thread.currentThread().getName());
	}

}
