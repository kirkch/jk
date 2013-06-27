package com.mosaic.jk.io;

import com.mosaic.jk.config.RepositoryRef;
import com.mosaic.jk.utils.Function1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public interface ProjectWorkspace {
    public File getRootProjectDirectory();

    public File[] scanForSourceDirectories();

    public File[] scanForTestDirectories( String moduleNameNbl );

    public boolean hasDependenciesFile();

    public void loadIniFile( String dependencies, IniFileDelegate iniFileDelegate ) throws IOException;
    public Properties loadPropertiesFile(String fileName);

    public void writePropertiesFile(String fileName, Properties properties);

    public List<RepositoryRef> loadRepositories();
}
