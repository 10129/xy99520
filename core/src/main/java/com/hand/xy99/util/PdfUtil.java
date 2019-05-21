package com.hand.xy99.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hand.xy99.test.dto.ProjectResource;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class PdfUtil {
    public PdfUtil() {
    }

    public void outputPdfFile(OutputStream os, Map<String, Map<String, List<ProjectResource>>> result) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        String ossys = System.getProperty("os.name");
        if (ossys.toLowerCase().startsWith("win")) {
            fontResolver.addFont("C:/Windows/fonts/simsun.ttc", "Identity-H", false);
        } else {
            fontResolver.addFont("/usr/share/fonts/simsun.ttc", "Identity-H", false);
        }

        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"UTF-8\"/>\n  <title>预算汇总</title>\n  <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width\"/>\n</head>\n<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\">\n<div style=\"max-width:600px;margin:0 auto;padding:10px;\">\n");
        int j = 0;
        if (result != null) {
            Iterator entries = result.entrySet().iterator();

            while(entries.hasNext()) {
                Entry entry = (Entry)entries.next();
                String[] aTitle = entry.getKey().toString().split(":");
                Map<String, List<ProjectResource>> map2 = (Map)entry.getValue();
                Iterator mapEntries = map2.entrySet().iterator();

                while(mapEntries.hasNext()) {
                    ++j;
                    Entry mapEntry = (Entry)mapEntries.next();
                    String[] bTitle = mapEntry.getKey().toString().split(":");
                    List<Map<String, Object>> projectResourcelist = (List)mapEntry.getValue();

                    for(int i = 0; i < projectResourcelist.size(); ++i) {
                        String resourceName = (String)((Map)projectResourcelist.get(i)).get("resourceName");
                        String resourceDesc = (String)((Map)projectResourcelist.get(i)).get("resourceDesc");
                        if (resourceDesc == null) {
                            resourceDesc = "";
                        }

                        Integer externalCount = (Integer)((Map)projectResourcelist.get(i)).get("externalCount");
                        if (externalCount == null) {
                            externalCount = 0;
                        }

                        String resourceUom = (String)((Map)projectResourcelist.get(i)).get("resourceUom");
                        if (resourceUom == null) {
                            resourceUom = "";
                        }

                        Integer dayNumber = (Integer)((Map)projectResourcelist.get(i)).get("dayNumber");
                        if (dayNumber == null) {
                            dayNumber = 0;
                        }

                        Integer times = (Integer)((Map)projectResourcelist.get(i)).get("times");
                        if (times == null) {
                            times = 0;
                        }

                        BigDecimal budgetPrice = (BigDecimal)((Map)projectResourcelist.get(i)).get("budgetPrice");
                        int budgetPrice1 = budgetPrice.intValue();
                        BigDecimal money = BigDecimal.valueOf((long)(externalCount * times * dayNumber)).multiply(budgetPrice);
                        int money1 = money.intValue();
                        new CnUpperCaser();
                        String strNum = CnUpperCaser.SectionNumToChn(j);
                        int a = i + 1;
                        html.append("  <div style='clear:both;border-bottom:1px dashed #999'>\n    <div style='font-size:20px;font-weight:bold;height44px;line-height:44px;margin-top:15px;'> <span>" + strNum + "、" + aTitle[0] + "</span> <div style='padding-left:15px; display:inline;color:red!important;'>￥" + aTitle[1] + "</div>  <span>" + aTitle[2] + "</span></div>\n" + "    <div style='font-size:18px;font-weight:bold;height44px;line-height:44px;text-indent:2em;'><span>" + a + "、" + bTitle[0] + "：</span> <div style='padding-left:15px; color:red!important;display:inline;'>￥" + bTitle[1] + "</div></div>\n" + "    <div class=\"\">\n" + "      <table style='width:100%;margin-top:15px;' class=\"table-separate\">\n" + "        <thead style='margin-top:15px;color:rgb(117, 134, 151);font-weight:700;'>\n" + "        <th style='width:16%;text-align:center;'>名称</th>\n" + "        <th style='width:14%;text-align:center;'>描述</th>\n" + "        <th style='width:14%;text-align:center;'>数量</th>\n" + "        <th style='width:14%;text-align:center;'>天数</th>\n" + "        <th style='width:14%;text-align:center;'>次数</th>\n" + "        <th style='width:14%;text-align:center;'>单价</th>\n" + "        <th style='width:14%;text-align:center;'>费用</th>\n" + "        </thead>\n" + "        <tbody>\n" + "        <tr  style='border:0;color:rgb(117, 134, 151);'>\n" + "          <td style='width:16%; height:30px;text-align:center;'><span>" + resourceName + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + resourceDesc + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + externalCount + resourceUom + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + dayNumber + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + times + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + budgetPrice1 + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + money1 + "</span></td>\n" + "        </tr>\n" + "        </tbody>\n" + "      </table>\n" + "    </div>\n" + "  </div>\n");
                    }
                }
            }
        }else {
            html.append(
                    "  <div style='clear:both;border-bottom:1px dashed #999'>\n" +
                            "    <div style='font-size:20px;font-weight:bold;height44px;line-height:44px;margin-top:15px;'> <span>"+1+"、"+321+"</span> <div style='padding-left:15px; display:inline;color:red!important;'>￥"+13213+"</div>  <span>"+123+"</span></div>\n" +
                            "    <div style='font-size:18px;font-weight:bold;height44px;line-height:44px;text-indent:2em;'><span>"+1+"、"+312+"：</span> <div style='padding-left:15px; color:red!important;display:inline;'>￥"+123+"</div></div>\n" +
                            "    <div class=\"\">\n" +
                            "      <table style='width:100%;margin-top:15px;' class=\"table-separate\">\n" +
                            "        <thead style='margin-top:15px;color:rgb(117, 134, 151);font-weight:700;'>\n" +
                            "        <th style='width:16%;text-align:center;'>名称</th>\n" +
                            "        <th style='width:14%;text-align:center;'>描述</th>\n" +
                            "        <th style='width:14%;text-align:center;'>数量</th>\n" +
                            "        <th style='width:14%;text-align:center;'>天数</th>\n" +
                            "        <th style='width:14%;text-align:center;'>次数</th>\n" +
                            "        <th style='width:14%;text-align:center;'>单价</th>\n" +
                            "        <th style='width:14%;text-align:center;'>费用</th>\n" +
                            "        </thead>\n" +
                            "        <tbody>\n" +
                            "        <tr  style='border:0;color:rgb(117, 134, 151);'>\n" +
                            "          <td style='width:16%; height:30px;text-align:center;'><span>"+321+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+321+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+312+321+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+321+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+31+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+321+"</span></td>\n" +
                            "          <td style='width:14%; height:30px;text-align:center;'><span>"+31+"</span></td>\n" +
                            "        </tr>\n" +
                            "        </tbody>\n" +
                            "      </table>\n" +
                            "    </div>\n" +
                            "  </div>\n"
            );
        }

        html.append("</div>\n\n</body>\n</html>");
        renderer.setDocumentFromString(html.toString());
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
    }

    public InputStream uploadFilePdfFile(Map<String, Map<String, List<ProjectResource>>> result) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        String ossys = System.getProperty("os.name");
        if (ossys.toLowerCase().startsWith("win")) {
            fontResolver.addFont("C:/Windows/fonts/simsun.ttc", "Identity-H", false);
        } else {
            fontResolver.addFont("/usr/share/fonts/simsun.ttc", "Identity-H", false);
        }

        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"UTF-8\"/>\n  <title>预算汇总</title>\n  <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width\"/>\n</head>\n<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\">\n<div style=\"max-width:600px;margin:0 auto;padding:10px;\">\n");
        int j = 0;
        if (result != null) {
            Iterator entries = result.entrySet().iterator();

            while(entries.hasNext()) {
                Entry entry = (Entry)entries.next();
                String[] aTitle = entry.getKey().toString().split(":");
                Map<String, List<ProjectResource>> map2 = (Map)entry.getValue();
                Iterator mapEntries = map2.entrySet().iterator();

                while(mapEntries.hasNext()) {
                    ++j;
                    Entry mapEntry = (Entry)mapEntries.next();
                    String[] bTitle = mapEntry.getKey().toString().split(":");
                    List<Map<String, Object>> projectResourcelist = (List)mapEntry.getValue();

                    for(int i = 0; i < projectResourcelist.size(); ++i) {
                        String resourceName = (String)((Map)projectResourcelist.get(i)).get("resourceName");
                        String resourceDesc = (String)((Map)projectResourcelist.get(i)).get("resourceDesc");
                        if (resourceDesc == null) {
                            resourceDesc = "";
                        }

                        Integer externalCount = (Integer)((Map)projectResourcelist.get(i)).get("externalCount");
                        if (externalCount == null) {
                            externalCount = 0;
                        }

                        String resourceUom = (String)((Map)projectResourcelist.get(i)).get("resourceUom");
                        if (resourceUom == null) {
                            resourceUom = "";
                        }

                        Integer dayNumber = (Integer)((Map)projectResourcelist.get(i)).get("dayNumber");
                        if (dayNumber == null) {
                            dayNumber = 0;
                        }

                        Integer times = (Integer)((Map)projectResourcelist.get(i)).get("times");
                        if (times == null) {
                            times = 0;
                        }

                        BigDecimal budgetPrice = (BigDecimal)((Map)projectResourcelist.get(i)).get("budgetPrice");
                        int budgetPrice1 = budgetPrice.intValue();
                        BigDecimal money = BigDecimal.valueOf((long)(externalCount * times * dayNumber)).multiply(budgetPrice);
                        int money1 = money.intValue();
                        new CnUpperCaser();
                        String strNum = CnUpperCaser.SectionNumToChn(j);
                        int a = i + 1;
                        html.append("  <div style='clear:both;border-bottom:1px dashed #999'>\n    <div style='font-size:20px;font-weight:bold;height44px;line-height:44px;margin-top:15px;'> <span>" + strNum + "、" + aTitle[0] + "</span> <div style='padding-left:15px; display:inline;color:red!important;'>￥" + aTitle[1] + "</div>  <span>" + aTitle[2] + "</span></div>\n" + "    <div style='font-size:18px;font-weight:bold;height44px;line-height:44px;text-indent:2em;'><span>" + a + "、" + bTitle[0] + "：</span> <div style='padding-left:15px; color:red!important;display:inline;'>￥" + bTitle[1] + "</div></div>\n" + "    <div class=\"\">\n" + "      <table style='width:100%;margin-top:15px;' class=\"table-separate\">\n" + "        <thead style='margin-top:15px;color:rgb(117, 134, 151);font-weight:700;'>\n" + "        <th style='width:16%;text-align:center;'>名称</th>\n" + "        <th style='width:14%;text-align:center;'>描述</th>\n" + "        <th style='width:14%;text-align:center;'>数量</th>\n" + "        <th style='width:14%;text-align:center;'>天数</th>\n" + "        <th style='width:14%;text-align:center;'>次数</th>\n" + "        <th style='width:14%;text-align:center;'>单价</th>\n" + "        <th style='width:14%;text-align:center;'>费用</th>\n" + "        </thead>\n" + "        <tbody>\n" + "        <tr  style='border:0;color:rgb(117, 134, 151);'>\n" + "          <td style='width:16%; height:30px;text-align:center;'><span>" + resourceName + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + resourceDesc + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + externalCount + resourceUom + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + dayNumber + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + times + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + budgetPrice1 + "</span></td>\n" + "          <td style='width:14%; height:30px;text-align:center;'><span>" + money1 + "</span></td>\n" + "        </tr>\n" + "        </tbody>\n" + "      </table>\n" + "    </div>\n" + "  </div>\n");
                    }
                }
            }
        }

        html.append("</div>\n\n</body>\n</html>");
        renderer.setDocumentFromString(html.toString());
        renderer.layout();
        renderer.createPDF(os);
        ByteArrayInputStream in = this.parse(os);
        os.flush();
        os.close();
        return in;
    }

    public ByteArrayInputStream parse(OutputStream out) throws Exception {
        new ByteArrayOutputStream();
        ByteArrayOutputStream baos = (ByteArrayOutputStream)out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
}
