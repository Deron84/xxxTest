package com.huateng.struts.query.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;

/**
 * 
 * @author 78098
 * 
 */
public class T50918Action extends BaseExcelQueryAction {

    private String year;

    private String mon;

    private String name;

    private String date;// 开始时间

    private String date2;// 结束时间

    private String mchtNo;// 商户号

    private String brhId;// 机构号

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getMchtNo() {
        return mchtNo;
    }

    public void setMchtNo(String mchtNo) {
        this.mchtNo = mchtNo;
    }

    @Override
    protected void deal() {

        String brh_query = "select m.BRH_ID,m.BRH_NAME from tbl_brh_info m start with m.BRH_ID='" + brhId
                + "' connect by  m.UP_BRH_ID=prior m.BRH_ID";
        List brhList = CommonFunction.getCommQueryDAO().findBySQLQuery(brh_query);
        // 机构信息存储：机构ID-机构名称
        Map<String, String> brh_map = new HashMap<String, String>();
        Object[] tmp = null;
        // 机构ID存储
        String brh_ids = "";
        for (int i = 0; i < brhList.size(); i++) {
            tmp = (Object[]) brhList.get(i);
            brh_map.put(tmp[0].toString(), tmp[1].toString());
            if (i == 0) {
                brh_ids = tmp[0].toString();
            } else {
                brh_ids = brh_ids + "," + tmp[0].toString();
            }
        }

        String wheresql = "";
        //机构号、商户号、商户名称、交易日期、交易笔数、交易金额、交易原始金额、交易优惠金额
        String deal_query = "select a.AGR_BR,a.MCH_NO,a.MCHT_NM,a.TRANS_DATE,a.TRANS_CNT,a.TRANS_MONEY,a.TRANS_ORIGMONEY,a.TRANS_DISCMONEY from  STA_TRANS_MCHTPERDAY a where a.AGR_BR in ("
                + brh_ids + ") and a.TRANS_DATE >= '" + date + "' and a.TRANS_DATE <= '" + date2 + "'";
        if (mchtNo != null && !mchtNo.equals("")) {
            deal_query = deal_query + " and a.MCH_NO='" + mchtNo + "'";
        }
        deal_query = deal_query + " order by a.AGR_BR,a.MCH_NO，a.TRANS_DATE asc";

        //表头处理
        c = sheet.getRow(1).getCell(3);
        if (c == null) {
            sheet.getRow(1).createCell(3);
        }
        if (sheet.getRow(1).getCell(1) == null) {
            sheet.getRow(1).createCell(1);
        }
        sheet.getRow(1).getCell(1).setCellValue(InformationUtil.getBrhName(brhId));
        sheet.getRow(1).getCell(3).setCellValue(date + " - " + date2);

        int rowNum = 3, cellNum = 0, columnNum = 9;
        fillData(deal_query, rowNum, cellNum, columnNum, brh_map);
    }

    public String getBrhId() {
        return brhId;
    }

    public void setBrhId(String brhId) {
        this.brhId = brhId;
    }

    @Override
    protected String getFileKey() {
        return ExcelName.EN_518;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public int fillData(String sql, int rowNum, int cellNum, int columnNum, Map<String, String> brh_map) {

        long count = 0;//交易笔数
        double sum0 = 0;//交易金额
        double sum1 = 0;//交易原始金额
        double sum2 = 0;//交易优惠金额

        List countList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        if (countList != null && countList.size() > 0) {
            for (int index = 0, size = countList.size(); index < size; index++) {
                Object[] record = (Object[]) countList.get(index);

                r = sheet.getRow(rowNum + index);
                if (r == null) {
                    r = sheet.createRow(rowNum + index);
                }
                //处理机构名称
                c = r.getCell(cellNum);
                if (c == null) {
                    c = r.createCell(cellNum);
                }
                c.setCellValue(brh_map.get(record[0]));

                for (int j = 1; j < columnNum; j++) {
                    String v = String.valueOf(record[j - 1]);
                    if (StringUtils.isEmpty(v) || "null".equals(v)) {// 这个是必须的，不然没数据会报错，不知道为什么没数据查出来的是"null"
                        v = "0";// 表示没数据
                    }
                    v = StringUtils.trim(v);
                    c = r.getCell(cellNum + j);
                    if (c == null) {
                        c = r.createCell(cellNum + j);
                    }

                    if (j == 5) {
                        c.setCellValue(Long.parseLong(v));
                        count = count + Long.parseLong(v);
                    } else if (j == 6) {
                        c.setCellValue(Double.parseDouble(v) / 100);
                        sum0 = sum0 + Double.parseDouble(v) / 100;
                    } else if (j == 7) {
                        c.setCellValue(Double.parseDouble(v) / 100);
                        sum1 = sum1 + Double.parseDouble(v) / 100;
                    } else if (j == 8) {
                        c.setCellValue(Double.parseDouble(v) / 100);
                        sum2 = sum2 + Double.parseDouble(v) / 100;
                    } else {
                        c.setCellValue(v);
                    }
                }
            }
        }
        //合计
        r = sheet.getRow(countList.size() + rowNum);
        if (r == null) {
            r = sheet.createRow(countList.size() + rowNum);
        }
        c = r.getCell(cellNum);
        if (c == null) {
            c = r.createCell(cellNum);
        }
        c.setCellValue("合计：");
        //交易总笔数
        c = r.getCell(5);
        if (c == null) {
            c = r.createCell(5);
        }
        c.setCellValue(count);
        //交易总金额
        c = r.getCell(6);
        if (c == null) {
            c = r.createCell(6);
        }
        c.setCellValue(sum0);
        //原始金额
        c = r.getCell(7);
        if (c == null) {
            c = r.createCell(7);
        }
        c.setCellValue(sum1);
        //优惠金额
        c = r.getCell(8);
        if (c == null) {
            c = r.createCell(8);
        }
        c.setCellValue(sum2);

        if (countList != null) {
            return countList.size();
        } else {
            return 0;
        }
    }

}