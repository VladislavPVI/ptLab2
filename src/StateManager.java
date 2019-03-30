import javax.xml.bind.*;
import java.io.File;
import java.math.BigInteger;

public class StateManager {

    private File file;
    private JAXBContext context;
    public StateManager() throws JAXBException {
        this.file = new File("state.xml");
        this.context = JAXBContext.newInstance(BigIntState.class);
    }
    public BigInteger getState() throws JAXBException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return ((BigIntState)unmarshaller.unmarshal(file)).getState();
    }
    public void setState(BigInteger state) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(new BigIntState(state), file);
    }
}
