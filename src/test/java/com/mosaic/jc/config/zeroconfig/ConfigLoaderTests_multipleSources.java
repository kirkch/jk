package com.mosaic.jc.config.zeroconfig;

import com.mosaic.jc.config.BaseConfigTestCase;
import com.mosaic.jc.config.Dependency;
import com.mosaic.jc.config.DependencyScope;
import com.mosaic.jc.config.ModuleConfig;
import com.mosaic.jc.utils.Function1;
import com.mosaic.jc.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mosaic.jc.TestUtils.assertArrayEqualsIgnoreOrder;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ConfigLoaderTests_multipleSources extends BaseConfigTestCase {

    public ConfigLoaderTests_multipleSources() {
        super("zeroConfig/multipleSourcesZeroConfig");
    }


    @Test
    public void defaultGroupId() {
        assertEquals( "com.mosaic", config.groupId );
    }

    @Test
    public void defaultProjectName() {
        assertEquals( "Multiple Sources Zero Config", config.projectName );
    }

    @Test
    public void defaultVersion() {
        assertEquals( "0.0.1."+System.getProperty("user.name")+"_1", config.versionFull);
    }

    @Test
    public void expectOnlyOneModule() {
        assertEquals( 2, config.modules.size() );
    }

    @Test
    public void expectImplicitModule_thatIsNoModuleName() {
        List<String> moduleNames = ListUtils.map(config.modules, new Function1<ModuleConfig, String>() {
            public String invoke(ModuleConfig m) {
                return m.moduleNameNbl;
            }
        });

        Collections.sort(moduleNames);

        assertEquals( Arrays.asList("client","server"), moduleNames );
    }

    @Test
    public void expectServerMainClass() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "server");
            }
        });

        assertArrayEquals( new String[] {"com.mosaic.server.Main"}, module.mainFQNs );
    }

    @Test
    public void expectNoClientMainClass() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "client");
            }
        });

        assertArrayEquals( new String[] {}, module.mainFQNs );
    }

    @Test
    public void expectClientSourceDirectory() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "client");
            }
        });

        File expectedSourceDir = new File(zeroConfigProjectDir, "src/client");

        assertArrayEqualsIgnoreOrder( new File[] {expectedSourceDir}, module.sourceDirectories );
    }

    @Test
    public void expectServerSourceDirectory() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "server");
            }
        });

        File expectedSourceDir = new File(zeroConfigProjectDir, "src/server");

        assertArrayEqualsIgnoreOrder( new File[] {expectedSourceDir}, module.sourceDirectories );
    }

    @Test
    public void expectClientTestSourceDirectory() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "client");
            }
        });

        File expectedSourceDir = new File(zeroConfigProjectDir, "tests/client");

        assertArrayEqualsIgnoreOrder( new File[] {expectedSourceDir}, module.testDirectories );
    }

    @Test
    public void expectTestServerSourceDirectory() {
        ModuleConfig module = ListUtils.selectFirstMatch(config.modules, new Function1<ModuleConfig, Boolean>() {
            public Boolean invoke(ModuleConfig m) {
                return StringUtils.equals(m.moduleNameNbl, "server");
            }
        });

        File expectedSourceDir = new File(zeroConfigProjectDir, "tests/server");

        assertArrayEqualsIgnoreOrder( new File[] {expectedSourceDir}, module.testDirectories );
    }

    @Test
    public void expectSingleJar() {
        ModuleConfig module = config.modules.get(0);

        assertEquals( "JAR", module.packageType );
    }

    @Test
    public void expectDefaultTestDependenciesOnly() {
        ModuleConfig module = config.modules.get(0);

        Dependency junit   = new Dependency( DependencyScope.TEST, "junit", "junit", "4.8.2" );
        Dependency mockito = new Dependency( DependencyScope.TEST, "org.mockito", "mockito-all", "1.9.5" );


        assertEquals( Arrays.asList( junit, mockito ), module.dependencies );
    }

}
