package com.example.toan.readnewspaper.dantri;

import com.example.toan.readnewspaper.ListContent;
import com.example.toan.readnewspaper.R;

/**
 * Created by toan on 15/12/2016.
 */

public class DantriListTest extends ListContent {
    @Override
    protected void InitValue() {
        super.InitValue();
        String web_[] = {
                "trang chu",
                "the thao",
                "bong da anh",
                "bong da tay ban nha",
                "bong da chau au",
                "kinh doanh",
                "doanh nghiep",///
                "thi truong",
                "quoc te",
                "tai chinh dau tu",
                "khoi nghiep",
                "phan mem bao mat"
        };

        int imageId_[] = {
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5,
                R.drawable.image6,
                R.drawable.image7,/////
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5,
                R.drawable.image6,
                R.drawable.image7

        };
        String linkRead_[] = {
                "http://dantri.com.vn/trangchu.rss",
                "http://dantri.com.vn/the-thao.rss",
                "http://dantri.com.vn/the-thao/bong-da-anh.rss",
                "http://dantri.com.vn/the-thao/bong-da-tbn.rss",
                "http://dantri.com.vn/the-thao/bong-da-chau-au.rss",
                "http://dantri.com.vn/kinh-doanh.rss",
                "http://dantri.com.vn/kinh-doanh/doanh-nghiep.rss",
                "http://dantri.com.vn/kinh-doanh/thi-truong.rss",
                "http://dantri.com.vn/kinh-doanh/quoc-te.rss",
                "http://dantri.com.vn/kinh-doanh/tai-chinh-dau-tu.rss",
                "http://dantri.com.vn/kinh-doanh/khoi-nghiep.rss",
                "http://dantri.com.vn/suc-manh-so/phan-mem-bao-mat.rss"
        };
        for(int i = 0; i < web_.length ; i ++){
            web[i] = web_[i];
            imageId[i] = imageId_[i];
            linkRead[i] = linkRead_[i];
        }
    }
}
