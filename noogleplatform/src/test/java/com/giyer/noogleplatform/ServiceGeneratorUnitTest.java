package com.giyer.noogleplatform;

import com.giyer.noogleplatform.dao.GetNewsResponse;
import com.giyer.noogleplatform.endpoints.APIClient;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertNotNull;

/**
 * Unit test for the ServiceGenerator Class
 */
public class ServiceGeneratorUnitTest {

    @Test
    public void testServiceGenerator() throws Exception {
        APIClient client = ServiceGenerator.createService(APIClient.class);
        Call<GetNewsResponse> testCall = client.getNews("json","Infosys","social.linkedin.shares");

        Response<GetNewsResponse> response = testCall.execute();
        assertNotNull(response);
        assertNotNull(client);
    }
}