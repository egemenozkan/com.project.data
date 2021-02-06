package com.project.data.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.project.api.data.model.common.Address;
import com.project.api.data.model.common.Contact;
import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.place.Localisation;
import com.project.api.data.model.place.Place;

@Mapper
public interface PlaceMapper {

	final String SELECT_PLACE = "SELECT pv.id, pv.main_image_id, pv.id AS place_id, pv.type, IF(#{language} IS NULL or #{language} = '', 'TR', #{language}) AS language, pv.address_id," + 
			"CASE IF(#{language} IS NULL or #{language} = '', 'TR', #{language})" + 
			" WHEN 'TR' THEN pv.tr_name WHEN 'EN' THEN pv.en_name WHEN 'RU' THEN pv.ru_name WHEN 'DE' THEN pv.de_name END AS name," + 
			" CASE IF(#{language} IS NULL or #{language} = '', 'TR', #{language})" + 
			" WHEN 'TR' THEN pv.tr_slug WHEN 'EN' THEN pv.en_slug WHEN 'RU' THEN pv.ru_slug WHEN 'DE' THEN pv.de_slug END AS slug" + 
			" ,pv.create_datetime, pv.update_datetime, pv.opening_time, pv.closing_time, pv.twenty_four_seven" +
			" FROM project.place_view_v2 pv";

	
	@Select("SELECT pv.id, pv.main_image_id, pv.id AS place_id, pv.type, IF(#{language} IS NULL or #{language} = '', 'TR', #{language}) AS language, pv.address_id," + 
			"CASE IF(#{language} IS NULL or #{language} = '', 'TR', #{language})" + 
			" WHEN 'TR' THEN pv.tr_name WHEN 'EN' THEN pv.en_name WHEN 'RU' THEN pv.ru_name WHEN 'DE' THEN pv.de_name END AS name," + 
			" CASE IF(#{language} IS NULL or #{language} = '', 'TR', #{language})" + 
			" WHEN 'TR' THEN pv.tr_slug WHEN 'EN' THEN pv.en_slug WHEN 'RU' THEN pv.ru_slug WHEN 'DE' THEN pv.de_slug END AS slug" + 
			" ,pv.create_datetime, pv.update_datetime, pv.opening_time, pv.closing_time, pv.twenty_four_seven" +
			" FROM project.place_view_v2 pv ORDER BY pv.update_datetime")
	@Results(value = {@Result(property = "type", column = "type", javaType = com.project.api.data.enums.PlaceType.class, typeHandler = com.project.data.mybatis.typehandler.PlaceTypeTypeHandler.class),
			@Result(property = "language", column = "language", javaType = com.project.common.enums.Language.class, typeHandler = com.project.data.mybatis.typehandler.LanguageTypeHandler.class)})
	List<Place> findAllPlace(String language);
	

	
	@Select(SELECT_PLACE + " WHERE type ${types}")
	@Results(value = {@Result(property = "type", column = "type", javaType = com.project.api.data.enums.PlaceType.class, typeHandler = com.project.data.mybatis.typehandler.PlaceTypeTypeHandler.class),
			@Result(property = "address", column = "address_id", javaType = Address.class, one = @One(select = "findAddressById")),
			@Result(property = "contact", column = "place_id", javaType = Contact.class, one = @One(select = "findContactByPlaceId")),
			@Result(property="images", column="place_id", javaType=List.class, many=@Many(select="findAllImagesByPlaceId")),
			@Result(property="mainImage", column="main_image_id", javaType=MyFile.class, one = @One(select="findMainImage")),
			@Result(property = "language", column = "language", javaType = com.project.common.enums.Language.class, typeHandler = com.project.data.mybatis.typehandler.LanguageTypeHandler.class)})
	List<Place> findAllPlaceByMainType(String language, String types);
	
	@Select("SELECT av.id, av.address_title, av.address, av.post_code, av.lat, av.lng, av.region_id, av.district_id, av.city_id FROM project.address_view_v2 av WHERE av.id = #{id}")
	Address findAddressById(long id);
	
	
	@Select("SELECT pn.name, pn.language, pn.slug FROM project.place_name pn WHERE pn.place_id = #{id}")
	@Results(value = {@Result(property = "language", column = "language", javaType = com.project.common.enums.Language.class, typeHandler = com.project.data.mybatis.typehandler.LanguageTypeHandler.class)})
	List<Localisation> findAllPlaceNameByPlaceId(long id);
	

	
}