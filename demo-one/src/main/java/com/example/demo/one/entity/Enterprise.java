package com.example.demo.one.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.example.demo.one.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuxq
 * @since 2018-11-23 14:20
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("enterprise")
public class Enterprise extends BaseEntity {

    public final static String TYPE_OF_PRODUCT = "生产企业";
    public final static String TYPE_OF_TRAFFIC = "运输企业";
    public final static String TYPE_OF_SOLVE = "处置企业";
    /**
     * 单位名称
     */
    @TableField("enterprise_name")
    @ApiModelProperty("企业名称")
    private String enterpriseName;
    /**
     * 公司类型：内资企业、国有企业、集体企业、股份合作企业、联营企业、有限责任公司、股份有限公司、私营企业、其他企业、港、澳、台商投资企业、合资经营企业（港或澳、台资）、合作经营企业（港或澳、台资）、港、澳、台商独资经营企业、港、澳、台商投资股份有限公司、其他港、澳、台商投资企业、外商投资企业、中外合资经营企业、中外合作经营企业、外资企业、外商投资股份有限公司、其他外商投资企业
     */
    @ApiModelProperty(value = "企业类型：", notes = "内资企业、国有企业、集体企业、股份合作企业、联营企业、有限责任公司、股份有限公司、私营企业、其他企业、港、澳、台商投资企业、合资经营企业（港或澳、台资）、合作经营企业（港或澳、台资）、港、澳、台商独资经营企业、港、澳、台商投资股份有限公司、其他港、澳、台商投资企业、外商投资企业、中外合资经营企业、中外合作经营企业、外资企业、外商投资股份有限公司、其他外商投资企业")
    private String type;
    /**
     * 企业业务：运输、生产、仓储企业、危化品生产企业、危化品经营企业、危化品使用企业、危化品使用企业、易制毒化学品企业、危化品存储企业、危化品运输企业、其他危险化学品企业；
     */
    @TableField("business_type")
    @ApiModelProperty(value = "企业业务类型：", notes = "运输、生产、仓储企业、危化品生产企业、危化品经营企业、危化品使用企业、危化品使用企业、易制毒化学品企业、危化品存储企业、危化品运输企业、其他危险化学品企业")
    private String businessType;
    /**
     * 目前是企业主要是危险化学品类别，以后还涉及到一般工贸企业如服装厂、零配件企业
     */
    @TableField("industry_category")
    @ApiModelProperty(value = "工业类别", notes = "目前是企业主要是危险化学品类别，以后还涉及到一般工贸企业如服装厂、零配件企业")
    private String industryCategory;
    /**
     * 统一信用代码
     */
    @TableField("credit_code")
    @ApiModelProperty("统一信用代码")
    private String creditCode;
    /**
     * 成立日期
     */
    @TableField("establish_date")
    @ApiModelProperty("成立日期")
    private Date establishDate;
    /**
     * 注册地址，一企一档
     */
    @ApiModelProperty("企业注册地址")
    private String address;
    /**
     * 经营地址，一企一档
     */
    @TableField("production_address")
    @ApiModelProperty("企业经营地址")
    private String productionAddress;

    @TableField("economic_type")
    @ApiModelProperty("企业经济类型")
    private String economicType;
    /**
     * 单位电话
     */
    @ApiModelProperty("企业联系电话")
    private String phone;
    /**
     * 邮编
     */
    @TableField("postal_code")
    @ApiModelProperty("邮编")
    private String postalCode;
    /**
     * 许可证有效期
     */
    @TableField("business_term")
    @ApiModelProperty("许可证有效期")
    private String businessTerm;
    /**
     * 传真号码
     */
    @TableField("fax_number")
    @ApiModelProperty("传真号码")
    private String faxNumber;
    /**
     * 企业法人
     */
    @TableField("legal_person")
    @ApiModelProperty("企业法人")
    private String legalPerson;
    /**
     * 企业法人联系电话
     */
    @TableField("legal_number")
    @ApiModelProperty("企业法人联系电话")
    private String legalNumber;
    /**
     * 企业法人电子邮件
     */
    @TableField("legal_email")
    @ApiModelProperty("企业法人电子邮件")
    private String legalEmail;
    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal latitude;
    /**
     * 行政区划代码
     */
    @TableField("political_divide_code")
    @ApiModelProperty("行政区划代码")
    private String politicalDivideCode;
    /**
     * 行政区划名称
     */
    @TableField("political_divide_name")
    @ApiModelProperty("行政区划名称")
    private String politicalDivideName;

}
