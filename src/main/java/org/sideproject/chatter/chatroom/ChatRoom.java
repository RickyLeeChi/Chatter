package org.sideproject.chatter.chatroom;

public class ChatRoom implements Runnable{
	
	private Integer roomId;
	
	private String roomName;
	
	public ChatRoom(String roomName, int roomId) {
		this.roomName = roomName;
		this.roomId = roomId;
	}
	
	@Override
	public void run() {		
		System.out.println("Inside : " + Thread.currentThread().getName());
		System.out.println("Chat room ID : " + this.getRoomId());
	}
	
	private Integer getRoomId() {
		return this.roomId;
	}
	
	private void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}
