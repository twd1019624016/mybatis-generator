package mybatis_plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

public class CommentPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	private boolean addRemarkComments = true;

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {

		field.addJavaDocLine("/**");
		String remarks = introspectedColumn.getRemarks();
		if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));
			for (String remarkLine : remarkLines) {
				field.addJavaDocLine(" *   " + remarkLine);
			}
		}
		field.addJavaDocLine(" *   ");

		StringBuilder sb = new StringBuilder(" *   ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		field.addJavaDocLine(sb.toString());
		field.addJavaDocLine(" */");

		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
	}

	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

		StringBuilder sb = new StringBuilder();

		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *   ");

		sb.setLength(0);
		sb.append(" * @return the value of ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" */");
		return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
				modelClassType);
	}

	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

		StringBuilder sb = new StringBuilder();

		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *   ");

		Parameter parm = method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		sb.append(" the value for ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" */");
		return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
				modelClassType);
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * "); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" *   " + remarkLine);  //$NON-NLS-1$
            }
        }
        topLevelClass.addJavaDocLine(" *   "); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder(" *   ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());

        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	
	

}
