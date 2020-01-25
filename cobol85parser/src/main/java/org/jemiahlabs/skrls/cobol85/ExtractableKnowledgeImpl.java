package org.jemiahlabs.skrls.cobol85;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.CobolKdmMapper;
import org.jemiahlabs.skrls.kdm.mapper.division.impl.DataDivisionHandler;
import org.jemiahlabs.skrls.kdm.mapper.division.impl.EnvironmentDivisionHandler;
import org.jemiahlabs.skrls.kdm.mapper.division.impl.IdentificationDivisionHandler;
import org.jemiahlabs.skrls.kdm.mapper.division.impl.ProcedureDivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.util.Counter;
import org.jemiahlabs.skrls.kdm.models.util.ListFilesUtil;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.runner.impl.CobolParserRunnerImpl;
import io.proleap.cobol.preprocessor.CobolPreprocessor;

public class ExtractableKnowledgeImpl implements ExtractableKnowledge {
	
	@Override
	public void extractKDM(Producer producer, String source, String outputDir) {
		File outputDirFile = new File(outputDir);
		if(!outputDirFile.exists() || !outputDirFile.isDirectory()) {
			producer.emitErrorMessage("Output directory not found");
			return;
		}
		
		CobolKdmMapper kdmMapper = new CobolKdmMapper(producer);
		configMapper(kdmMapper);
		
		CobolPreprocessor.CobolSourceFormatEnum format = CobolPreprocessor.CobolSourceFormatEnum.TANDEM; 
		
		ListFilesUtil listFilesUtil = new ListFilesUtil( (file) -> {
			try {
				Program program = new CobolParserRunnerImpl(producer).analyzeFile(file, format);
				List<CompilationUnit> compilationUnits = program.getCompilationUnits();
				
				compilationUnits.forEach((compilationUnit) -> {
					KDMSegment model = kdmMapper.process(compilationUnit);
					exportXML(producer, model, outputDirFile);
					Counter.getGlobalCounter().reset();
				});
				
			} catch (IOException e) {
				producer.emitWarningMessage(e.getMessage());
			} catch (RuntimeException ex) {
				producer.emitErrorMessage(ex.getMessage());
			}
		});
		
		listFilesUtil.listFiles(source, (file) -> file.getName().endsWith(".cbl") || file.getName().endsWith(".CBL"));
	}
	
	private void configMapper(CobolKdmMapper kdmMapper) {
		kdmMapper.addHandlerDefinition(DataDivisionHandler.class);
		kdmMapper.addHandlerDefinition(EnvironmentDivisionHandler.class);
		kdmMapper.addHandlerDefinition(ProcedureDivisionHandler.class);
		kdmMapper.addHandlerDefinition(IdentificationDivisionHandler.class);
	}
	
	private void exportXML(Producer producer, KDMSegment model, File outputDirFile) {
		XStream xstream = new XStream(new StaxDriver());
		xstream.autodetectAnnotations(true);
		String xml = xstream.toXML(model);
		String formatedXML = formatXML(xml);
		try {
			saveFile(formatedXML, Paths.get(outputDirFile.getAbsolutePath(), model.getName() + ".xmi.kdm").toFile());
			producer.emitInfoMessage("Generated file " + model.getName());
		} catch (IOException e) {
			producer.emitErrorMessage("Error in XML Write: " + e.getMessage());
		}
	}
	
	private String formatXML(String xml) {
		try {
			Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();

			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			Source xmlSource = new SAXSource(new InputSource(
					new ByteArrayInputStream(xml.getBytes())));
			StreamResult res = new StreamResult(new ByteArrayOutputStream());            

			serializer.transform(xmlSource, res);

			return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());

		} catch(Exception e) {
			return xml;
		}
	}
	
	private void saveFile(String xml, File outputFile) throws IOException {
		FileOutputStream fos = null;
		fos = new FileOutputStream(outputFile);
        byte[] bytes = xml.getBytes("UTF-8");
        fos.write(bytes);
        fos.close();
	}
}
