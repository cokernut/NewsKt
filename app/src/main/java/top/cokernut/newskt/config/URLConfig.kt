package top.cokernut.newskt.config

import java.util.ArrayList

import top.cokernut.newskt.model.URLModel

/**
 * Created by Cokernut on 2016/6/7.
 */
object URLConfig {

    /**

     * JSON返回数据
     * {
     * "code": 200,
     * "msg": "ok",
     * "newslist": [
     * {
     * "ctime": "2015-07-17",
     * "title": "那个抱走王明涵的，你上微信吗？看完这个你会心软吗？",
     * "description": "中国传统文化",
     * "picUrl": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-667708.jpg/640",
     * "url": "http://mp.weixin.qq.com/s?__biz=MzA3OTg2NjEwNg==&amp;idx=5&amp;mid=209313388&amp;sn=7e30bd2851d22f69580e202c31fc7ecf"
     * },
     * {
     * "ctime": "2015-06-12",
     * "title": "深悦地产风云榜丨房地产微信公众号一周榜单",
     * "description": "深悦会",
     * "picUrl": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-530408.jpg/640",
     * "url": "http://mp.weixin.qq.com/s?__biz=MjM5NTI4NDk0Mg==&amp;idx=4&amp;mid=206963932&amp;sn=595e66f68648b86fba04fbc3a58e623c"
     * },
     * {
     * "ctime": "2015-06-14",
     * "title": "一条微信向全世界宣告，这就是惠州！",
     * "description": "西子湖畔",
     * "picUrl": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-536516.jpg/640",
     * "url": "http://mp.weixin.qq.com/s?__biz=MjM5NTAzMDQ0MA==&amp;idx=1&amp;mid=209423088&amp;sn=fc5c230b38e4485a01bdc7693714047b"
     * },
     * ]
     * }

     */

    private val urls = ArrayList<URLModel>()
    val BASE_URL = "http://api.tianapi.com/"
    val API_KEY = "fc7d0977e4da244cfa45cb10a483f745"

    init {
        urls.add(URLModel("科技新闻", "keji"))
        urls.add(URLModel("移动互联", "mobile"))
        urls.add(URLModel("IT资讯", "it"))
        urls.add(URLModel("社会新闻", "social"))
        urls.add(URLModel("体育新闻", "tiyu"))
        urls.add(URLModel("国际新闻", "world"))
        urls.add(URLModel("国内新闻", "guonei"))
        urls.add(URLModel("娱乐花边", "huabian"))
        urls.add(URLModel("美女图片", "meinv"))
        urls.add(URLModel("奇闻趣事", "qiwen"))
        urls.add(URLModel("生活健康", "health"))
    }

    fun getUrls(): ArrayList<URLModel> {
        return urls
    }
}
