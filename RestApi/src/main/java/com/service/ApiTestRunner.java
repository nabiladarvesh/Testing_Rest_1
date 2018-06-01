package com.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;

public class ApiTestRunner extends JUnitStories{
	
	private final CrossReference xref = new CrossReference();
	@Override  
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        // factory to allow parameter conversion and loading from external
        // resources (used by StoryParser too)
        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(),
                new LoadFromClasspath(embeddableClass), parameterConverters);
        // add custom converters
        parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));
 
	 /*return super.configuration()  
			 .useStoryControls(new StoryControls().doDryRun(false).doSkipScenariosAfterFailure(false))
             //.useStoryLoader(new LoadFromClasspath(embeddableClass))
             .useStoryParser(new RegexStoryParser(examplesTableFactory))
             .useStoryPathResolver(new UnderscoredCamelCaseResolver())
             .useStoryReporterBuilder(
                     new StoryReporterBuilder()
                             .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                             .withDefaultFormats().withPathResolver(new ResolveToPackagedName())
                             .withViewResources(viewResources).withFormats(Format.CONSOLE, Format.TXT, Format.HTML, Format.XML)
                             .withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref))
             .useParameterConverters(parameterConverters)
             // use '%' instead of '$' to identify parameters
             .useStepPatternParser(new RegexPrefixCapturingPatternParser("%"))
             .useStepMonitor(xref.getStepMonitor());*/
        
        return new MostUsefulConfiguration()
                .useStoryPathResolver(storyPathResolver())
                .useStoryLoader(storyLoader())
                .useStoryReporterBuilder(storyReporterBuilder())
                .useParameterControls(parameterControls());
	} 
	
	private StoryPathResolver storyPathResolver() {
        return new UnderscoredCamelCaseResolver();
    }
	
	private StoryLoader storyLoader() {
        return new LoadFromClasspath();
    }
	
	private StoryReporterBuilder storyReporterBuilder() {
        return new StoryReporterBuilder()
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                .withFailureTrace(true)
                .withDefaultFormats()
                .withFormats(Format.IDE_CONSOLE, Format.TXT, Format.HTML);
    }

    private ParameterControls parameterControls() {
        return new ParameterControls()
                .useDelimiterNamedParameters(true);
    }

	@Override  
	public InjectableStepsFactory stepsFactory() {  
	  return new InstanceStepsFactory(configuration(), new CountryService_Steps(), new StateService_Steps());  
	}  

	@Override  
	protected List<String> storyPaths() {  
	  //return new  StoryFinder().findPaths("C:\\Users\\Nabs\\eclipse-workspace\\RestApi\\src\\main\\resources\\CountryService\\", "Countries.story", "");
		return new  StoryFinder().findPaths(CodeLocations.codeLocationFromClass(
				   this.getClass()), 
				   Arrays.asList("**/*.story"), 
				   Arrays.asList(""));
	  } 
}
