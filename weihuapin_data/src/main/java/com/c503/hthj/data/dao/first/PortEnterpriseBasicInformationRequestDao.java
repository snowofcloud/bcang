package com.c503.hthj.data.dao.first;

import com.c503.hthj.data.domain.PortEnterpriseBasicInformation;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class PortEnterpriseBasicInformationRequestDao {

    /**查询数据*/
    public PortEnterpriseBasicInformation queryData(String ywjbxxid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_xzxk_gkqyjbxx  where YWJBXXID = ?";
        PortEnterpriseBasicInformation pebi = queryRunner.query(sql, new BeanHandler<PortEnterpriseBasicInformation>
                (PortEnterpriseBasicInformation.class), ywjbxxid);
        return pebi;
    }

    /**插入数据*/
    public void saveData(PortEnterpriseBasicInformation pebi) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into edi_v_xzxk_gkqyjbxx (YWJBXXID,JBXX_JBRLXDH,JBXX_QYLXR,JBXX_SFZH,JBXX_YYZZBH,JBXX_BZ," +
                "JBXX_JJDY,JBXX_GKJYXKZYXQ,JBXX_SFZYGKYW,JBXX_ZCD,JBXX_ZCDXXDZ,JBXX_GKSSJYR,JBXX_JBR,JBXX_QYJJLXFL," +
                "JBXX_QYJJLXZL,JBXX_ZCZB,JBXX_CZ,JBXX_GKJYXKZHSWSZ,JBXX_GKJYXKZFZRQ,JBXX_YX,JBXX_GSDZ,JBXX_FR,JBXX_ZCZBDW," +
                "JBXX_GKJYXXFW,JBXX_GDZC,JBXX_QYLXRDH,JBXX_YB,JBXX_FRLXDH,JBXX_QYJC,JBXX_SQJYNX,JBXX_QYZYLX,JBXX_GKJYXKZH," +
                "JBXX_GKJYFW,JBXX_CSGKWXHWZY) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params ={pebi.getYWJBXXID(),pebi.getJBXX_JBRLXDH(),pebi.getJBXX_QYLXR(),pebi.getJBXX_SFZH(),
                pebi.getJBXX_YYZZBH(),pebi.getJBXX_BZ(),pebi.getJBXX_JJDY(),pebi.getJBXX_GKJYXKZYXQ(),
                pebi.getJBXX_SFZYGKYW(),pebi.getJBXX_ZCD(),pebi.getJBXX_ZCDXXDZ(),pebi.getJBXX_GKSSJYR(),pebi.getJBXX_JBR(),
                pebi.getJBXX_QYJJLXFL(),pebi.getJBXX_QYJJLXZL(),pebi.getJBXX_ZCZB(),pebi.getJBXX_CZ(),pebi.getJBXX_GKJYXKZHSWSZ(),
                pebi.getJBXX_GKJYXKZFZRQ(),pebi.getJBXX_YX(),pebi.getJBXX_GSDZ(),pebi.getJBXX_FR(),pebi.getJBXX_ZCZBDW(),
                pebi.getJBXX_GKJYXXFW(),pebi.getJBXX_GDZC(),pebi.getJBXX_QYLXRDH(),pebi.getJBXX_YB(),pebi.getJBXX_FRLXDH(),
                pebi.getJBXX_QYJC(),pebi.getJBXX_SQJYNX(),pebi.getJBXX_QYZYLX(),pebi.getJBXX_GKJYXKZH(),pebi.getJBXX_GKJYFW(),
                pebi.getJBXX_CSGKWXHWZY()};
        queryRunner.update(sql,params);
    }
}

















