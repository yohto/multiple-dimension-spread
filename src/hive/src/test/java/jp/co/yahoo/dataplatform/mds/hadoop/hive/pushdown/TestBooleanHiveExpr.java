/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.yahoo.dataplatform.mds.hadoop.hive.pushdown;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import jp.co.yahoo.dataplatform.mds.spread.expression.ExecuterNode;

import org.apache.hadoop.hive.serde2.typeinfo.*;
import org.apache.hadoop.hive.ql.plan.*;
import org.apache.hadoop.hive.ql.udf.generic.*;

public class TestBooleanHiveExpr{

  @Test
  public void T_newInstance_1(){
    ExprNodeColumnDesc nodeColumnDesc = new ExprNodeColumnDesc( TypeInfoFactory.booleanTypeInfo , "col1" , "col1" , false  );
    BooleanHiveExpr expr = new BooleanHiveExpr( nodeColumnDesc );
  }

  @Test
  public void T_newInstance_2(){
    GenericUDFIndex udfIndex = new GenericUDFIndex();
    List<ExprNodeDesc> childList = new ArrayList<ExprNodeDesc>();
    childList.add( new ExprNodeColumnDesc( TypeInfoFactory.booleanTypeInfo , "col1" , "col1" , false  ) );
    childList.add( new ExprNodeConstantDesc( TypeInfoFactory.stringTypeInfo , "child_name" ) );
    ExprNodeGenericFuncDesc funcDesc = new ExprNodeGenericFuncDesc( TypeInfoFactory.booleanTypeInfo , udfIndex , childList );
    System.out.println( funcDesc.toString() );
    BooleanHiveExpr expr = new BooleanHiveExpr( funcDesc );

    assertTrue( expr.getPushDownFilterNode() instanceof ExecuterNode);
  }

  @Test
  public void T_addChild_1(){
    ExprNodeColumnDesc nodeColumnDesc = new ExprNodeColumnDesc( TypeInfoFactory.booleanTypeInfo , "col1" , "col1" , false  );
    System.out.println( nodeColumnDesc.toString() );
    BooleanHiveExpr expr = new BooleanHiveExpr( nodeColumnDesc );
    assertThrows( UnsupportedOperationException.class ,
      () -> {
        expr.addChildNode( null );
      }
    );
  }

}
