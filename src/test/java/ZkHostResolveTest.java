import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2017/08/11
 * Time: 6:32 PM
 * Desc:
 */
public class ZkHostResolveTest {
    public static void main(String[] args) throws Exception {
        InetSocketAddress isa = InetSocketAddress.createUnresolved("kerberos.mit.edu", 2181);
        System.out.println(isa);
        System.out.println(isa.getHostString());
        InetAddress ia = isa.getAddress();
        InetAddress ias[] = InetAddress.getAllByName((ia != null) ? ia.getHostAddress() : isa.getHostName());
        System.out.println(ias);
        for(InetAddress addr : ias) {
            if(addr.toString().startsWith("/") && addr.getAddress() != null)
                System.out.println("1-> " + InetAddress.getByAddress(isa.getHostName(), addr.getAddress()) + ":" + isa.getPort());
            else
                System.out.println("2-> " + addr.getHostAddress() + ":" + isa.getPort() + "\n" + new InetSocketAddress(addr.getHostAddress(), isa.getPort()));
        }
    }
}
