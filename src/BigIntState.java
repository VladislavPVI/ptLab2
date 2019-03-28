import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

@XmlRootElement
public class BigIntState {
    private BigInteger state;
    public BigIntState() {
        this.state = BigInteger.valueOf(0);
    }
    public BigIntState(BigInteger state) {
        this.state = state;
    }
    public BigInteger getState() {
        return state;
    }
    public void setState(BigInteger state) {
        this.state = state;
    }
}