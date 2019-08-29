/**
 * 
 */
package mybatis_plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;

public class BatchInsertPlugin extends PluginAdapter {

	private FullyQualifiedJavaType listInstance;
	public BatchInsertPlugin() {
        super();
        listInstance = new FullyQualifiedJavaType("java.util.List"); //$NON-NLS-1$
    }
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {

		if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
			addInsertMethod(interfaze, introspectedTable);
		}

		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
			addElements(document.getRootElement(), introspectedTable);
		}
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private void addElements(XmlElement parentElement, IntrospectedTable introspectedTable) {
		
		XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", "batchInsert")); //$NON-NLS-1$

		answer.addAttribute(new Attribute("parameterType",listInstance.getFullyQualifiedName()));

		context.getCommentGenerator().addComment(answer);

		GeneratedKey gk = introspectedTable.getGeneratedKey();
		if (gk != null) {
			IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
			if (introspectedColumn != null) {
				if (gk.isJdbcStandard()) {
					answer.addAttribute(new Attribute("useGeneratedKeys", "true")); //$NON-NLS-1$ //$NON-NLS-2$
					answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
				} else {
					answer.addElement(getSelectKey(introspectedColumn, gk));
				}
			}
		}

		StringBuilder insertClause = new StringBuilder();
		StringBuilder valuesClause = new StringBuilder();

		insertClause.append(" insert into "); //$NON-NLS-1$
		insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		OutputUtilities.newLine(insertClause);
		insertClause.append(" ("); //$NON-NLS-1$

		valuesClause.append("values ");
		OutputUtilities.newLine(valuesClause);
		valuesClause.append("<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" > ("); 
		OutputUtilities.newLine(valuesClause);
		

		List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());

		Iterator<IntrospectedColumn> iter = columns.iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			if (introspectedColumn.isIdentity()) {
				continue;
			}

			insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
			
			if (iter.hasNext()) {
				insertClause.append(", "); 
				valuesClause.append(", "); 
			}

			if (valuesClause.length() > 80) {
				/*answer.addElement(new TextElement(insertClause.toString()));
				insertClause.setLength(0);
				OutputUtilities.xmlIndent(insertClause, 1);*/

				valuesClauses.add(valuesClause.toString());
				valuesClause.setLength(0);
				OutputUtilities.xmlIndent(valuesClause, 1);
			}
		}

		insertClause.append(')');
		answer.addElement(new TextElement(insertClause.toString()));
		
		valuesClauses.add(valuesClause.toString());
		valuesClauses.add(")");
		valuesClauses.add("</foreach>");

		for (String clause : valuesClauses) {
			answer.addElement(new TextElement(clause));
		}

		parentElement.addElement(answer);
	}

	protected XmlElement getSelectKey(IntrospectedColumn introspectedColumn, GeneratedKey generatedKey) {
		String identityColumnType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();

		XmlElement answer = new XmlElement("selectKey"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("resultType", identityColumnType)); //$NON-NLS-1$
		answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
		answer.addAttribute(new Attribute("order", //$NON-NLS-1$
				generatedKey.getMyBatis3Order()));

		answer.addElement(new TextElement(generatedKey.getRuntimeSqlStatement()));

		return answer;
	}

	private void addInsertMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("void"));
		method.setName("batchInsert");
		
		
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
	    importedTypes.add(listInstance);
	    
		FullyQualifiedJavaType parameterType;
		parameterType = introspectedTable.getRules().calculateAllFieldsClass();

		importedTypes.add(parameterType);

		FullyQualifiedJavaType listParamType = new FullyQualifiedJavaType("List<" + parameterType + ">");
		method.addParameter(new Parameter(listParamType, "recordList")); //$NON-NLS-1$

		
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

	}
}
