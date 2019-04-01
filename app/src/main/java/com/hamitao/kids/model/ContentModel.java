package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/23.
 */

public class ContentModel {


    /**
     * responseDataObj : {"contents":[{"authorList":["baike"],"categoryList":["小小音乐家","常识"],"characteristicList":["磨耳朵","英文","欢快"],"contentid":"erge.wesingandmove","contentlang":"zh","contenttype":"file","descriptionI18List":[{"language":"zh","value":"儿歌"}],"duration":3592,"favourited":"no","imgauthorurl":"","imgauthorurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=rmXminOA%2FOmt%2Fhtd%2FxSoungRIB4%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","imgtitleurl":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/imgtitle.jpg","imgtitleurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/imgtitle.jpg?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=Za1e4CSbgm7a%2FZkSNZcyOe4ae88%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","keywordList":["儿歌","英语","关键字"],"likeCount":0,"lyric":"","mediaList":[{"duration":56,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/02%20Old%20Brass%20Wagon.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=%2FbclN0LlQs2HcEFl4Er4zLTEwak%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/02 Old Brass Wagon.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-02 Old Brass Wagon.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"02 Old Brass Wagon","mediatype":"audio","resolution":""},{"duration":60,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/03%20Pop%21%20Goes%20the%20Weasel.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=rn2Yn6bvf0agNRViueUSXsIkSNM%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/03 Pop! Goes the Weasel.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-03 Pop! Goes the Weasel.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"03 Pop! Goes the Weasel","mediatype":"audio","resolution":""},{"duration":34,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/04%20Sally%20Go%20Round%20the%20Sun.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=SBdzFoim0rE9palnQPXy5uXdjC4%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/04 Sally Go Round the Sun.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-04 Sally Go Round the Sun.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"04 Sally Go Round the Sun","mediatype":"audio","resolution":""},{"duration":73,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/05%20Punchinello.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=PecsNoLLF9wMDGWZD45QheNbX1c%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/05 Punchinello.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-05 Punchinello.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"05 Punchinello","mediatype":"audio","resolution":""},{"duration":29,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/06%20The%20Merry-Go%20Round.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=j1RygLfVpfUdp69MMBNcWTeEHUk%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/06 The Merry-Go Round.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-06 The Merry-Go Round.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"06 The Merry-Go Round","mediatype":"audio","resolution":""},{"duration":61,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/07%20Ring%20Around%20the%20Rosy.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=nlnA8lG%2BdFJ0PB3PiOXK62NpsEg%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/07 Ring Around the Rosy.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-07 Ring Around the Rosy.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"07 Ring Around the Rosy","mediatype":"audio","resolution":""},{"duration":150,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/08%20The%20Hokey%20Posey.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=4OWxah78XKvVK1K7QcMdXNc10O4%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/08 The Hokey Posey.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-08 The Hokey Posey.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"08 The Hokey Posey","mediatype":"audio","resolution":""},{"duration":126,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/09%20Jim%20Along%2C%20Josie.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=8HFAJP%2BRMmMdWaODpVhadD5yPoo%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/09 Jim Along, Josie.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-09 Jim Along, Josie.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"09 Jim Along, Josie","mediatype":"audio","resolution":""},{"duration":224,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/10%20The%20Toy%20Shop%20At%20Midnight.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=cu52nTe4XkPvahWfdBehCSWoP2Y%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/10 The Toy Shop At Midnight.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-10 The Toy Shop At Midnight.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"10 The Toy Shop At Midnight","mediatype":"audio","resolution":""},{"duration":59,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/11%20Reach%20For%20the%20Sky.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=YOKHRSdFNmrGSljxQfVrQpItx%2Fk%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/11 Reach For the Sky.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-11 Reach For the Sky.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"11 Reach For the Sky","mediatype":"audio","resolution":""},{"duration":73,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/12%20Peter%20Hammers.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=FSb7ko1uz%2B5qzVpENTa35XOxDVI%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/12 Peter Hammers.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-12 Peter Hammers.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"12 Peter Hammers","mediatype":"audio","resolution":""},{"duration":107,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/13%20Hey%2C%20Mr.%20Knickerbocker.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=CeXXnxAa2ETyhzbVIJkrXsTBtZg%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/13 Hey, Mr. Knickerbocker.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-13 Hey, Mr. Knickerbocker.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":" Knickerbocker","mediatype":"audio","resolution":""},{"duration":84,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/14%20Airplane.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=V2mWwCII6OOTqrFr0vdOj2885XI%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/14 Airplane.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-14 Airplane.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"14 Airplane","mediatype":"audio","resolution":""},{"duration":73,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/15%20Head%20and%20Shoulders%20Chant.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=dR9tElDInTDk3tr2aY9E7Z%2F3WOo%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/15 Head and Shoulders Chant.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-15 Head and Shoulders Chant.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"15 Head and Shoulders Chant","mediatype":"audio","resolution":""},{"duration":53,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/16%20Head%20and%20Shoulders.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=C0O%2BpqFmpUq7THnvHFFihDGj1FU%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/16 Head and Shoulders.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-16 Head and Shoulders.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"16 Head and Shoulders","mediatype":"audio","resolution":""},{"duration":49,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/17%20Walking%2C%20Walking.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=HPCYsPVQ09JIvDRuMk0dUolBeiA%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/17 Walking, Walking.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-17 Walking, Walking.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"17 Walking, Walking","mediatype":"audio","resolution":""},{"duration":49,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/18%20Teddy%20Bear.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=y4s8IrzGGTuvC7PVro6k3AP36i4%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/18 Teddy Bear.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-18 Teddy Bear.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"18 Teddy Bear","mediatype":"audio","resolution":""},{"duration":191,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/19%20The%20Land%20of%20Slow%20Motion.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=TQfq8WnipE7xSO5gXbrE88DZrB8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/19 The Land of Slow Motion.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-19 The Land of Slow Motion.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"19 The Land of Slow Motion","mediatype":"audio","resolution":""},{"duration":76,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/20%20Animal%20Action.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=HaUQiYF52ic7mCHFQQcCX2M7%2FEU%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/20 Animal Action.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-20 Animal Action.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"20 Animal Action","mediatype":"audio","resolution":""},{"duration":369,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/21%20A%20Walk%20Through%20the%20Forest.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=BrkRjHG3XPJewOvJmL0P3kYl0OQ%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/21 A Walk Through the Forest.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-21 A Walk Through the Forest.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"21 A Walk Through the Forest","mediatype":"audio","resolution":""},{"duration":168,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/22%20The%20Freeze%20Game.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=zfgeIcs9aRrySgcZIy2IbacJb58%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/22 The Freeze Game.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-22 The Freeze Game.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"22 The Freeze Game","mediatype":"audio","resolution":""},{"duration":85,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/23%20The%20Land%20of%20Silly.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=p6wMnzkEmMhE5vKBe13OmLR6mrM%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/23 The Land of Silly.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-23 The Land of Silly.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"23 The Land of Silly","mediatype":"audio","resolution":""},{"duration":102,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/24%20The%20Dance%20Contest.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=Vdnq6vFGrFdjTsJQfwg88Ut3FlQ%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/24 The Dance Contest.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-24 The Dance Contest.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"24 The Dance Contest","mediatype":"audio","resolution":""},{"duration":68,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/25%20The%20Dinosaur%20Party.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=5Twk5wtdepL0jYm2yhZt2pFOozA%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/25 The Dinosaur Party.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-25 The Dinosaur Party.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"25 The Dinosaur Party","mediatype":"audio","resolution":""},{"duration":85,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/26%20Brontosaurus.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=L%2FkdQKiDA07ep7WR9KhJJ9JhqSo%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/26 Brontosaurus.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-26 Brontosaurus.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"26 Brontosaurus","mediatype":"audio","resolution":""},{"duration":55,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/27%20Pteranodon.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=yaccAgVUU4tqHDKFo1t4rz6Ikdg%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/27 Pteranodon.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-27 Pteranodon.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"27 Pteranodon","mediatype":"audio","resolution":""},{"duration":39,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/28%20Ornithomimus.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=%2FwLRhXaFrc7AmLftsFDCaLSESAc%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/28 Ornithomimus.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-28 Ornithomimus.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"28 Ornithomimus","mediatype":"audio","resolution":""},{"duration":66,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/29%20The%20Cowboy-Old%20Texas.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=0XivH52VN5GRR0WDO8FHjP7qgYM%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/29 The Cowboy-Old Texas.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-29 The Cowboy-Old Texas.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"29 The Cowboy-Old Texas","mediatype":"audio","resolution":""},{"duration":36,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/30%20The%20Old%20Chisholm%20Trail.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=wfKyHH7pIuu7RCTtca5M4gxGkOM%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/30 The Old Chisholm Trail.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-30 The Old Chisholm Trail.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"30 The Old Chisholm Trail","mediatype":"audio","resolution":""},{"duration":54,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/31%20William%20Tell%20Overture.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=eN5NTCLfl%2BYNM4axOLZyfR6RiO8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/31 William Tell Overture.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-31 William Tell Overture.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"31 William Tell Overture","mediatype":"audio","resolution":""},{"duration":112,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/32%20Bugs.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=oA0gRd30cHjC%2BkYljOgWNV9Ir5w%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/32 Bugs.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-32 Bugs.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"32 Bugs","mediatype":"audio","resolution":""},{"duration":57,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/33%20Popcorn%20Ball.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=gb9NYLw%2BQab8kZxwsu4ZroyEKQw%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/33 Popcorn Ball.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-33 Popcorn Ball.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"33 Popcorn Ball","mediatype":"audio","resolution":""},{"duration":68,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/34%20Race%20Car.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=Cuq8Dsw%2B7FqjpkALwINAVTK%2BJZY%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/34 Race Car.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-34 Race Car.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"34 Race Car","mediatype":"audio","resolution":""},{"duration":83,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/35%20Bones.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=yoRz0xMSZ0D4vJgGQNIC2RFPl6E%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/35 Bones.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-35 Bones.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"35 Bones","mediatype":"audio","resolution":""},{"duration":145,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/36%20Follow%20Me.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=AzUW0WfUr0ah04e6561XL4letdo%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/36 Follow Me.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-36 Follow Me.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"36 Follow Me","mediatype":"audio","resolution":""},{"duration":105,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/37%20March.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=D4w5BKzmX1VROLehdFN%2BdCDtvq8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/37 March.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-37 March.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"37 March","mediatype":"audio","resolution":""},{"duration":125,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/38%20Hansel%20and%20Gretel%20Dance.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=fRhDLAIm01wIB2qJGyzrLwK%2F2uQ%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/38 Hansel and Gretel Dance.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-38 Hansel and Gretel Dance.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"38 Hansel and Gretel Dance","mediatype":"audio","resolution":""},{"duration":143,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%84%BF%E6%AD%8C/weesing%E8%8B%B1%E6%96%87%E5%84%BF%E6%AD%8C/20Wee_Sing-_and_Move%E4%B8%80%E8%BE%B9%E5%94%B1%E8%BE%B9%E8%B7%B3/Wee%20Sing-%20and%20Move/39%20The%20Animal%20Parade.mp3?Expires=1530673806&OSSAccessKeyId=STS.NJBfL6VdAhqp9gP7QhnehsUAM&Signature=4vai1Du5Jls1h2dIqZPHarmlgiM%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n3LfaCu7tg37ObO0Hh01E9YupEnJDqrzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPUJBbkSSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABAKzuNAGC%2FYTgAR6FcIJJwyhNfPTnvJZVaP4NQW3mcDwSKNE0ImHq6ADRxbO9NKfGtCcyAlzxoywu3xm62KvV5AhaOB5YhV6AdWswTWyVZnjsOATs00WYz1GgWI1xdUdy7OjOciJU8k71ITiJs13qoKODLCMofoqB4C6TMrLwj4g%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/儿歌/weesing英文儿歌/20Wee_Sing-_and_Move一边唱边跳/Wee Sing- and Move/39 The Animal Parade.mp3","mediacontentype":"keyonoss","mediaid":"erge.wesingandmove-39 The Animal Parade.mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"39 The Animal Parade","mediatype":"audio","resolution":""}],"mylike_id":"","nameI18List":[{"language":"zh","value":"一边唱一边跳"}],"orientUser":{"age_max":5,"age_min":2,"sex":"any"},"quality":"perfect","scenario":"misc","sourceorigin":"internet+http://www.google.com.cn/aaa","status":"enable","target":"app+device","vendor":"none"}]}
     * result : success
     */

    private ResponseDataObjBean responseDataObj;
    private String result;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ResponseDataObjBean {
        private List<ContentsBean> contents;

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public static class ContentsBean {

            private String contentid;
            private String contentlang;
            private String contenttype;
            private int duration;
            private String favourited;
            private String myfavouriteid;
            private String imgauthorurl;
            private String imgauthorurlhttpURL;
            private String imgtitleurl;
            private String imgtitleurlhttpURL;
            private int likeCount;
            private String lyric;
            private String mylike_id;
            private OrientUserBean orientUser;
            private String quality;
            private String scenario;
            private String sourceorigin;
            private String status;
            private String target;
            private String vendor;
            private List<String> authorList;
            private List<String> categoryList;
            private List<String> characteristicList;
            private List<DescriptionI18ListBean> descriptionI18List;
            private List<String> keywordList;
            private List<MediaListBean> mediaList;
            private List<NameI18ListBean> nameI18List;

            public String getContentid() {
                return contentid;
            }

            public void setContentid(String contentid) {
                this.contentid = contentid;
            }

            public String getContentlang() {
                return contentlang;
            }

            public void setContentlang(String contentlang) {
                this.contentlang = contentlang;
            }

            public String getContenttype() {
                return contenttype;
            }

            public void setContenttype(String contenttype) {
                this.contenttype = contenttype;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getFavourited() {
                return favourited;
            }

            public void setFavourited(String favourited) {
                this.favourited = favourited;
            }

            public String getMyfavouriteid() {
                return myfavouriteid;
            }

            public void setMyfavouriteid(String myfavouriteid) {
                this.myfavouriteid = myfavouriteid;
            }

            public String getImgauthorurl() {
                return imgauthorurl;
            }

            public void setImgauthorurl(String imgauthorurl) {
                this.imgauthorurl = imgauthorurl;
            }

            public String getImgauthorurlhttpURL() {
                return imgauthorurlhttpURL;
            }

            public void setImgauthorurlhttpURL(String imgauthorurlhttpURL) {
                this.imgauthorurlhttpURL = imgauthorurlhttpURL;
            }

            public String getImgtitleurl() {
                return imgtitleurl;
            }

            public void setImgtitleurl(String imgtitleurl) {
                this.imgtitleurl = imgtitleurl;
            }

            public String getImgtitleurlhttpURL() {
                return imgtitleurlhttpURL;
            }

            public void setImgtitleurlhttpURL(String imgtitleurlhttpURL) {
                this.imgtitleurlhttpURL = imgtitleurlhttpURL;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public String getLyric() {
                return lyric;
            }

            public void setLyric(String lyric) {
                this.lyric = lyric;
            }

            public String getMylike_id() {
                return mylike_id;
            }

            public void setMylike_id(String mylike_id) {
                this.mylike_id = mylike_id;
            }

            public OrientUserBean getOrientUser() {
                return orientUser;
            }

            public void setOrientUser(OrientUserBean orientUser) {
                this.orientUser = orientUser;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getScenario() {
                return scenario;
            }

            public void setScenario(String scenario) {
                this.scenario = scenario;
            }

            public String getSourceorigin() {
                return sourceorigin;
            }

            public void setSourceorigin(String sourceorigin) {
                this.sourceorigin = sourceorigin;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getVendor() {
                return vendor;
            }

            public void setVendor(String vendor) {
                this.vendor = vendor;
            }

            public List<String> getAuthorList() {
                return authorList;
            }

            public void setAuthorList(List<String> authorList) {
                this.authorList = authorList;
            }

            public List<String> getCategoryList() {
                return categoryList;
            }

            public void setCategoryList(List<String> categoryList) {
                this.categoryList = categoryList;
            }

            public List<String> getCharacteristicList() {
                return characteristicList;
            }

            public void setCharacteristicList(List<String> characteristicList) {
                this.characteristicList = characteristicList;
            }

            public List<DescriptionI18ListBean> getDescriptionI18List() {
                return descriptionI18List;
            }

            public void setDescriptionI18List(List<DescriptionI18ListBean> descriptionI18List) {
                this.descriptionI18List = descriptionI18List;
            }

            public List<String> getKeywordList() {
                return keywordList;
            }

            public void setKeywordList(List<String> keywordList) {
                this.keywordList = keywordList;
            }

            public List<MediaListBean> getMediaList() {
                return mediaList;
            }

            public void setMediaList(List<MediaListBean> mediaList) {
                this.mediaList = mediaList;
            }

            public List<NameI18ListBean> getNameI18List() {
                return nameI18List;
            }

            public void setNameI18List(List<NameI18ListBean> nameI18List) {
                this.nameI18List = nameI18List;
            }

            public static class OrientUserBean {
                /**
                 * age_max : 5
                 * age_min : 2
                 * sex : any
                 */

                private int age_max;
                private int age_min;
                private String sex;

                public int getAge_max() {
                    return age_max;
                }

                public void setAge_max(int age_max) {
                    this.age_max = age_max;
                }

                public int getAge_min() {
                    return age_min;
                }

                public void setAge_min(int age_min) {
                    this.age_min = age_min;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }
            }

            public static class DescriptionI18ListBean {
                /**
                 * language : zh
                 * value : 儿歌
                 */

                private String language;
                private String value;

                public String getLanguage() {
                    return language;
                }

                public void setLanguage(String language) {
                    this.language = language;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class MediaListBean {

                /**
                 * duration : 191
                 * favourited : yes
                 * headerimgurl :
                 * headerimgurlhttpURL :
                 * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%AE%89%E5%85%A8%E5%B0%8F%E5%84%BF%E6%AD%8C/%E4%BA%A4%E9%80%9A%E5%AE%89%E5%85%A8%E6%AD%8C-%E6%AE%B5%E4%B8%BD%E9%98%B3%E5%BD%AD%E9%87%8E.mp3?Expires=1530869875&OSSAccessKeyId=STS.NJzMUH4RMzcxMxAqoJFGw5Pyr&Signature=i%2B13ceg4l8jumGd%2FsHuLUODGLZ0%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4nPBu%2F82Y1szaGTT17wlW8fSshb2pXSkDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPa%2BIb%2FCSF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABDoLuLVU09MIfu0Mb6p3rxii%2BCqwrK6mzyU5K9Cx8YlGJrtf100OSJJtL9ludc6yNl9l%2BZ6x6aMnCB%2F3S7Xm%2Fuxy5ZTg0NwHFfRspK59UCrehXkOWd%2F0oL%2FFggq5mW0r%2FhKlQ98nZ6K7i1ZpaYxOCuhlDCzuquvY0D8UVBaVpPG4%3D
                 * lrcformat :
                 * mediacontent : contentstorage/解忧杂货店/安全/安全小儿歌/交通安全歌-段丽阳彭野.mp3
                 * mediacontentype : keyonoss
                 * mediaid : erge.anquanxiaoerge-交通安全歌-段丽阳彭野.mp3
                 * medialrc :
                 * medialrchttpURL :
                 * mediasubtype : mp3
                 * mediatitle : 交通安全歌-段丽阳彭野
                 * mediatype : audio
                 * myfavouriteid : favorite_cf866071cdee42d3bea97efe5116ad93
                 * resolution :
                 */

                private int duration;
                private String favourited;
                private String headerimgurl;
                private String headerimgurlhttpURL;
                private String httpURL;
                private String lrcformat;
                private String mediacontent;
                private String mediacontentype;
                private String mediaid;
                private String medialrc;
                private String medialrchttpURL;
                private String mediasubtype;
                private String mediatitle;
                private String mediatype;
                private String myfavouriteid;
                private String resolution;

//                private boolean isPlaying;
//
//                public boolean getIsPlaying() {
//                    return isPlaying;
//                }
//
//                public void setIsPlaying(boolean isPlaying) {
//                    this.isPlaying = isPlaying;
//                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public String getFavourited() {
                    return favourited;
                }

                public void setFavourited(String favourited) {
                    this.favourited = favourited;
                }

                public String getHeaderimgurl() {
                    return headerimgurl;
                }

                public void setHeaderimgurl(String headerimgurl) {
                    this.headerimgurl = headerimgurl;
                }

                public String getHeaderimgurlhttpURL() {
                    return headerimgurlhttpURL;
                }

                public void setHeaderimgurlhttpURL(String headerimgurlhttpURL) {
                    this.headerimgurlhttpURL = headerimgurlhttpURL;
                }

                public String getHttpURL() {
                    return httpURL;
                }

                public void setHttpURL(String httpURL) {
                    this.httpURL = httpURL;
                }

                public String getLrcformat() {
                    return lrcformat;
                }

                public void setLrcformat(String lrcformat) {
                    this.lrcformat = lrcformat;
                }

                public String getMediacontent() {
                    return mediacontent;
                }

                public void setMediacontent(String mediacontent) {
                    this.mediacontent = mediacontent;
                }

                public String getMediacontentype() {
                    return mediacontentype;
                }

                public void setMediacontentype(String mediacontentype) {
                    this.mediacontentype = mediacontentype;
                }

                public String getMediaid() {
                    return mediaid;
                }

                public void setMediaid(String mediaid) {
                    this.mediaid = mediaid;
                }

                public String getMedialrc() {
                    return medialrc;
                }

                public void setMedialrc(String medialrc) {
                    this.medialrc = medialrc;
                }

                public String getMedialrchttpURL() {
                    return medialrchttpURL;
                }

                public void setMedialrchttpURL(String medialrchttpURL) {
                    this.medialrchttpURL = medialrchttpURL;
                }

                public String getMediasubtype() {
                    return mediasubtype;
                }

                public void setMediasubtype(String mediasubtype) {
                    this.mediasubtype = mediasubtype;
                }

                public String getMediatitle() {
                    return mediatitle;
                }

                public void setMediatitle(String mediatitle) {
                    this.mediatitle = mediatitle;
                }

                public String getMediatype() {
                    return mediatype;
                }

                public void setMediatype(String mediatype) {
                    this.mediatype = mediatype;
                }

                public String getMyfavouriteid() {
                    return myfavouriteid;
                }

                public void setMyfavouriteid(String myfavouriteid) {
                    this.myfavouriteid = myfavouriteid;
                }

                public String getResolution() {
                    return resolution;
                }

                public void setResolution(String resolution) {
                    this.resolution = resolution;
                }
            }

            public static class NameI18ListBean {
                /**
                 * language : zh
                 * value : 一边唱一边跳
                 */

                private String language;
                private String value;

                public String getLanguage() {
                    return language;
                }

                public void setLanguage(String language) {
                    this.language = language;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
