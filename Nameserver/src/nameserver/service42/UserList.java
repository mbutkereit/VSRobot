package nameserver.service42;

import java.io.IOException;
import java.util.Vector;
import fi.iki.elonen.NanoWSD.WebSocket;
import fi.iki.elonen.NanoWSD.WebSocketFrame.CloseCode;

public class UserList {
    Vector<User> list;

    public UserList() {
        list = new Vector<User>();
    }

    public void addUser(User user) {
        list.add(user);
    }

    public void removeUser(User user) {
        list.remove(user);
    }

    public int userCount() {
        return list.size();
    }

    public void sendToAll(String str) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            WebSocket ws = user.webSocket;
            if (ws != null) {
                try {
                	ws.ping(str.getBytes());
                    //ws.send(str);
                } catch (IOException e) {
                    System.out.println("sending error.....");
                    try {
                        ws.close(CloseCode.InvalidFramePayloadData, "reqrement", false);
                    } catch (IOException e1) {
                        removeUser(user);
                    }
                }
            }
        }
    }

    public void disconectAll() {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            WebSocket ws = user.webSocket;
            if(ws != null){
                try {
                    ws.close(CloseCode.InvalidFramePayloadData, "reqrement", false);
                } catch (IOException e) {
                    removeUser(user);
                }
            }
        }
    }
}