package example;

import com.webservices.HelloWorldServiceLocator;
import com.webservices.HelloWorld_PortType;

public class HelloWorldClient {
  public static void main(String[] argv) {
      try {
          HelloWorldServiceLocator locator = new HelloWorldServiceLocator();
          HelloWorld_PortType service = locator.getHelloWorld();
          System.out.println(service.sayHelloWorldFrom("新郎：徐雪强；新娘：何芹"));
          System.out.println(service.sayHelloWorldFrom("在这普天同庆的日子里，喜结良缘，永结同好，早生贵子"));
          System.out.println(service.sayHelloWorldFrom("让我们祝愿两位新人辛福安康、合家欢乐"));
          //Activator service = locator.get();
          // If authorization is required
          //((HelloWorldSoapBindingStub)service).setUsername("user3");
          //((HelloWorldSoapBindingStub)service).setPassword("pass3");
          // invoke business method
          //service.businessMethod();


      } catch (javax.xml.rpc.ServiceException ex) {
          ex.printStackTrace();
      } catch (java.rmi.RemoteException ex) {
          ex.printStackTrace();
      }
  }
}
