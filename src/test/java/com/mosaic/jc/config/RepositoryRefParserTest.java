package com.mosaic.jc.config;

import com.mosaic.jc.config.RepositoryRef;
import com.mosaic.jc.config.RepositoryRefParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RepositoryRefParserTest {

    private RepositoryRefParser parser = new RepositoryRefParser();

    @Test
    public void urlOnly() {
        String url = "http://myrepo.mycompany.com/repository";
        RepositoryRef ref = parser.parseRef(url);

        assertEquals( "myrepo.mycompany.com Repository", ref.getName() );
        assertEquals( url, ref.getUrl() );
    }

    @Test
    public void urlOnlyWithWhitespace() {
        String url = "   http://myrepo.mycompany.com/repository \t  ";
        RepositoryRef ref = parser.parseRef(url);

        assertEquals( "http://myrepo.mycompany.com/repository", ref.getUrl() );
        assertEquals( "myrepo.mycompany.com Repository", ref.getName() );
    }

    @Test
    public void urlAndRepositoryName() {
        String url = " My Repo :  http://myrepo.mycompany.com/repository ";
        RepositoryRef ref = parser.parseRef(url);

        assertEquals( "My Repo", ref.getName() );
        assertEquals( "http://myrepo.mycompany.com/repository", ref.getUrl() );
    }

//    @Test  -- decided not to perform the URL check here for now (will revisit)
    public void invalidURL_expectException() {
        String url = "http://myrepo. mycompany.com/repository";
        RepositoryRef ref = parser.parseRef(url);

        assertEquals( url, ref.getUrl() );
    }



}
