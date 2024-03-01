package integration;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolverService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressResolverIT {

    private AddressResolverService resolver;
    private ISimpleHttpClient httpClient;

    @BeforeEach
    public void init(){
        httpClient = new TqsBasicHttpClient();
        resolver = new AddressResolverService(httpClient);        
    }


    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //TODO

        // repeat the same tests conditions from AddressResolverTest, without mocks
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        // return
        Address expected = new Address("AvenidadaUniversidade", "Aveiro", "3810-489", "");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        //TODO
        // repeat the same tests conditions from AddressResolverTest, without mocks

        Optional<Address> result = resolver.findAddressForLocation(-361, -361);
        // verify no valid result
        assertFalse(result.isPresent());
        
    }

}
