/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.hibernate.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.NvlFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;

/**
 * Title:oracle数据库方言
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class OracleDialect extends Dialect {
	
	static final String DEFAULT_BATCH_SIZE = "15";
	
	public OracleDialect() {
		super();
		registerColumnType( Types.BIT, "number(1,0)" );
		registerColumnType( Types.BIGINT, "number(19,0)" );
		registerColumnType( Types.SMALLINT, "number(5,0)" );
		registerColumnType( Types.TINYINT, "number(3,0)" );
		registerColumnType( Types.INTEGER, "number(10,0)" );
		registerColumnType( Types.CHAR, "char(1 char)" );
		registerColumnType( Types.VARCHAR, 4000, "varchar2($l char)" );
		registerColumnType( Types.VARCHAR, "long" );
		registerColumnType( Types.FLOAT, "float" );
		registerColumnType( Types.DOUBLE, "double precision" );
		registerColumnType( Types.DATE, "date" );
		registerColumnType( Types.TIME, "date" );
		registerColumnType( Types.TIMESTAMP, "timestamp" );
		registerColumnType( Types.VARBINARY, 2000, "raw($l)" );
		registerColumnType( Types.VARBINARY, "long raw" );
		registerColumnType( Types.NUMERIC, "number($p,$s)" );
		registerColumnType( Types.BLOB, "blob" );
		registerColumnType( Types.CLOB, "clob" );
		/** shen_antonio. */
		registerHibernateType( Types.CHAR, Hibernate.STRING.getName() );

		getDefaultProperties().setProperty(Environment.USE_STREAMS_FOR_BINARY, "true");
		getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE, DEFAULT_BATCH_SIZE);

		registerFunction( "abs", new StandardSQLFunction("abs") );
		registerFunction( "sign", new StandardSQLFunction("sign", Hibernate.INTEGER) );

		registerFunction( "acos", new StandardSQLFunction("acos", Hibernate.DOUBLE) );
		registerFunction( "asin", new StandardSQLFunction("asin", Hibernate.DOUBLE) );
		registerFunction( "atan", new StandardSQLFunction("atan", Hibernate.DOUBLE) );
		registerFunction( "cos", new StandardSQLFunction("cos", Hibernate.DOUBLE) );
		registerFunction( "cosh", new StandardSQLFunction("cosh", Hibernate.DOUBLE) );
		registerFunction( "exp", new StandardSQLFunction("exp", Hibernate.DOUBLE) );
		registerFunction( "ln", new StandardSQLFunction("ln", Hibernate.DOUBLE) );
		registerFunction( "sin", new StandardSQLFunction("sin", Hibernate.DOUBLE) );
		registerFunction( "sinh", new StandardSQLFunction("sinh", Hibernate.DOUBLE) );
		registerFunction( "stddev", new StandardSQLFunction("stddev", Hibernate.DOUBLE) );
		registerFunction( "sqrt", new StandardSQLFunction("sqrt", Hibernate.DOUBLE) );
		registerFunction( "tan", new StandardSQLFunction("tan", Hibernate.DOUBLE) );
		registerFunction( "tanh", new StandardSQLFunction("tanh", Hibernate.DOUBLE) );
		registerFunction( "variance", new StandardSQLFunction("variance", Hibernate.DOUBLE) );

		registerFunction( "round", new StandardSQLFunction("round") );
		registerFunction( "trunc", new StandardSQLFunction("trunc") );
		registerFunction( "ceil", new StandardSQLFunction("ceil") );
		registerFunction( "floor", new StandardSQLFunction("floor") );

		/** shen_antoni. */
		registerFunction( "chr", new StandardSQLFunction("chr", Hibernate.CHARACTER) );
		registerFunction( "initcap", new StandardSQLFunction("initcap") );
		registerFunction( "lower", new StandardSQLFunction("lower") );
		registerFunction( "ltrim", new StandardSQLFunction("ltrim") );
		registerFunction( "rtrim", new StandardSQLFunction("rtrim") );
		registerFunction( "soundex", new StandardSQLFunction("soundex") );
		registerFunction( "upper", new StandardSQLFunction("upper") );
		registerFunction( "ascii", new StandardSQLFunction("ascii", Hibernate.INTEGER) );
		registerFunction( "length", new StandardSQLFunction("length", Hibernate.LONG) );

		registerFunction( "to_char", new StandardSQLFunction("to_char", Hibernate.STRING) );
		registerFunction( "to_date", new StandardSQLFunction("to_date", Hibernate.TIMESTAMP) );

		registerFunction( "current_date", new NoArgSQLFunction("current_date", Hibernate.DATE, false) );
		registerFunction( "current_time", new NoArgSQLFunction("current_timestamp", Hibernate.TIME, false) );
		registerFunction( "current_timestamp", new NoArgSQLFunction("current_timestamp", Hibernate.TIMESTAMP, false) );
		
		registerFunction( "lastday", new StandardSQLFunction("lastday", Hibernate.DATE) );
		registerFunction( "sysdate", new NoArgSQLFunction("sysdate", Hibernate.DATE, false) );
		registerFunction( "systimestamp", new NoArgSQLFunction("systimestamp", Hibernate.TIMESTAMP, false) );
		registerFunction( "uid", new NoArgSQLFunction("uid", Hibernate.INTEGER, false) );
		registerFunction( "user", new NoArgSQLFunction("user", Hibernate.STRING, false) );

		registerFunction( "rowid", new NoArgSQLFunction("rowid", Hibernate.LONG, false) );
		registerFunction( "rownum", new NoArgSQLFunction("rownum", Hibernate.LONG, false) );

		// Multi-param string dialect functions...
		registerFunction( "concat", new VarArgsSQLFunction(Hibernate.STRING, "", "||", "") );
		registerFunction( "instr", new StandardSQLFunction("instr", Hibernate.INTEGER) );
		registerFunction( "instrb", new StandardSQLFunction("instrb", Hibernate.INTEGER) );
		registerFunction( "lpad", new StandardSQLFunction("lpad", Hibernate.STRING) );
		registerFunction( "replace", new StandardSQLFunction("replace", Hibernate.STRING) );
		registerFunction( "rpad", new StandardSQLFunction("rpad", Hibernate.STRING) );
		registerFunction( "substr", new StandardSQLFunction("substr", Hibernate.STRING) );
		registerFunction( "substrb", new StandardSQLFunction("substrb", Hibernate.STRING) );
		registerFunction( "translate", new StandardSQLFunction("translate", Hibernate.STRING) );

		registerFunction( "substring", new StandardSQLFunction( "substr", Hibernate.STRING ) );
		registerFunction( "locate", new StandardSQLFunction( "instr", Hibernate.INTEGER ) );
		registerFunction( "bit_length", new SQLFunctionTemplate( Hibernate.INTEGER, "vsize(?1)*8" ) );
		registerFunction( "coalesce", new NvlFunction() );

		// Multi-param numeric dialect functions...
		registerFunction( "atan2", new StandardSQLFunction("atan2", Hibernate.FLOAT) );
		registerFunction( "log", new StandardSQLFunction("log", Hibernate.INTEGER) );
		registerFunction( "mod", new StandardSQLFunction("mod", Hibernate.INTEGER) );
		registerFunction( "nvl", new StandardSQLFunction("nvl") );
		registerFunction( "nvl2", new StandardSQLFunction("nvl2") );
		registerFunction( "power", new StandardSQLFunction("power", Hibernate.FLOAT) );

		// Multi-param date dialect functions...
		registerFunction( "add_months", new StandardSQLFunction("add_months", Hibernate.DATE) );
		registerFunction( "months_between", new StandardSQLFunction("months_between", Hibernate.FLOAT) );
		registerFunction( "next_day", new StandardSQLFunction("next_day", Hibernate.DATE) );

		registerFunction( "str", new StandardSQLFunction("to_char", Hibernate.STRING) );
	}
}
