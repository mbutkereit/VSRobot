package interfaces;

public interface InterfaceIDLCaDSEV3RMINameserverRegistration {

   
    public int registerService(String serviceName, String ip, int port);

    public int unregisterService(int serviceName);

    public String lookup(String serviceName);


}
