package stubInterfaces;

public interface InterfaceIDLCaDSEV3RMINameserverRegistration {

   
    public int registerService(String serviceName, String ip, int port);

    public int unregisterService(String serviceName);

    public String lookup(String serviceName);


}
