package nameserver.service42;

class LebensChecker implements Runnable{
UserList list;
public LebensChecker(UserList userList){
	this.list = userList;
}
	
	
	@Override
	public void run() {
		try {
			while(true){
			Thread.sleep(4000);
			this.list.sendToAll("--Heartbeat--");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
} 