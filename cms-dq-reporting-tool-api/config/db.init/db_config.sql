CREATE TABLE cmsdq_reportingtool.crt_report_details
(
        `report_id`         bigint          NOT NULL AUTO_INCREMENT,
        `report_name`       varchar(200)    NOT NULL,
        `report_desc`       text            NULL,
        `last_run_date`     timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `user_id`           varchar(50)     NOT NULL,
        `email_id`          varchar(45)     DEFAULT NULL,
        `json_value`        longtext        NOT NULL,
        `percentage`        varchar(4000)   DEFAULT NULL,
        `status`            varchar(1)      DEFAULT NULL,
        `error_count`       int(11)         DEFAULT '0',
        `is_rerun`          varchar(5)      DEFAULT 'false',
        `date_created`      timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `date_modified`     timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

        PRIMARY KEY (`report_id`),
        KEY `IDX_RptId` (`report_id`) USING BTREE,
        KEY `IDX_UserID_RptName` (`user_id`,`report_name`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4 ;

ALTER TABLE cmsdq_reportingtool.crt_report_details MODIFY COLUMN status VARCHAR(1) NULL DEFAULT 'P';
ALTER TABLE cmsdq_reportingtool.crt_report_details MODIFY COLUMN last_run_date timestamp NULL DEFAULT CURRENT_TIMESTAMP ;

CREATE TABLE cmsdq_reportingtool.crt_version_log (
        `log_uuid`          char(36)        COLLATE     utf8_unicode_ci     NOT NULL,
        `report_id`         bigint          COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `logged_on`         datetime                                        DEFAULT NULL,
        `site`              varchar(255)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `stage`             varchar(100)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `content_type`      varchar(255)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `response_time`     varchar(50)     COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `version_id`        int(10)         unsigned                        DEFAULT NULL,
        `user_action`       varchar(100)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `json_value`        longtext        COLLATE     utf8_unicode_ci,
        `json_query`        longtext        COLLATE     utf8_unicode_ci,
        `log_type`          varchar(100)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `user_id`           varchar(100)    COLLATE     utf8_unicode_ci     DEFAULT NULL,
        `error_desc`        longtext        COLLATE     utf8_unicode_ci,
        'date_created'      timestamp                                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
        'date_modified'     timestamp                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

        PRIMARY KEY (`log_uuid`) USING BTREE,
        KEY `crt_versionlog_reportid` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

Stored Procedure
cmsdq_reportingtool.use_crt_getVersionInfo

DELIMITER $$
CREATE DEFINER=`arcrpt000plaglba`@`172.__.%` PROCEDURE `usp_crt_getVersionInfo`(_requestType varchar(100),
                                                                                _reportId char(36),
                                                                                _logId char(36),
                                                                                _siteName varchar(255),
                                                                                _contentType varchar(100),
                                                                                _userId varchar(100),
                                                                                _startDate varchar(50),
                                                                                _endDate varchar(50),
                                                                                _startIndex int,
                                                                                _noofRows int)
BEGIN

    set @row_num = 0;
    set _startIndex=_startIndex+1;

    IF _requestType="GetQueryCounts" THEN

        SELECT logtype,COUNT(1)as counts FROM cmsdq_reportingtool.crt_versionlog WHERE (logtype ='GenJsonQuery' or (logtype='GenReport' and useraction in ('Re-Run','SaveAndRun' ))) GROUP BY logtype order by logtype;

    ELSEIF _requestType="GetReportsByUserIdCount" THEN

        SELECT COUNT(1) AS counts from (SELECT count(1) FROM crt_versionlog v
                                                                 INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
                                        WHERE v.logtype='GenReport' and rd.userid=_userId
                                        GROUP BY rd.reportid)a;

    ELSEIF _requestType="GetReportsByUserId" THEN

        SELECT * FROM (SELECT @row_num := @row_num + 1 as row_number,a.* FROM (SELECT rd.reportId as ReportId,rd.reportname ReportName,v.loggedon DateModified,v.site Site,
            v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId FROM crt_versionlog v
            INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
            WHERE v.logtype='GenReport' and rd.userid=_userId
            GROUP BY rd.reportid)a
                       ORDER BY a.DateModified)resultset
        where  (row_number>=_startIndex and row_number<(_startIndex+_noofRows));

    ELSEIF _requestType="GetAllReportsCount" THEN

        SELECT count(1) as counts FROM (SELECT rd.reportId as ReportId,rd.reportname ReportName,v.loggedon DateModified,v.site Site,
                                               v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId FROM crt_versionlog v
                                                                                                                                              INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
                                        WHERE v.logtype='GenReport'
                                        GROUP BY rd.reportid)a ;

    ELSEIF _requestType="GetAllReports" THEN

        SELECT * FROM (SELECT @row_num := @row_num + 1 as row_number,a.* FROM (SELECT rd.reportId as ReportId,rd.reportname ReportName,max(v.loggedon) DateModified,v.site Site,
            v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId FROM crt_versionlog v
            INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
            WHERE v.logtype='GenReport'
            GROUP BY rd.reportid)a
                       ORDER BY a.DateModified desc)resultset
        where  (row_number>=_startIndex and row_number<(_startIndex+_noofRows));

    ELSEIF _requestType="GetVersionsByReportIdCount" THEN

        SELECT count(1) as counts FROM crt_versionlog v
                                           INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
        WHERE v.reportid=_reportId
        ORDER BY versionid;

    ELSEIF _requestType="GetVersionsByReportId" THEN

        SELECT * FROM (SELECT @row_num := @row_num + 1 as row_number,a.* FROM(SELECT v.loguuid LogUUID,rd.reportname ReportName,v.versionid Version,v.loggedon DateTimeAdded,v.site Site,
            v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid,v.useraction UserAction
            FROM crt_versionlog v
            INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
            WHERE v.reportid=_reportId
            ORDER BY versionid desc)a)resultset
        WHERE (row_number>=_startIndex and row_number<(_startIndex+_noofRows));

    ELSEIF _requestType="GetReportsByDateRange" THEN

        SELECT * FROM (SELECT @row_num := @row_num + 1 as row_number,a.* FROM(SELECT rd.reportname ReportName,v.loggedon DateModified,v.site Site,
            v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId
            FROM crt_versionlog v
            INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
            WHERE LastRunDate BETWEEN (DATE(_startDate)) AND (DATE_ADD(DATE(_endDate),INTERVAL 1 DAY))
            GROUP BY rd.reportid)a)resultset
        WHERE (row_number>=_startIndex and row_number<(_startIndex+_noofRows));

    ELSEIF _requestType="GetReportsByDateRangeCount" THEN

        SELECT COUNT(1) AS counts FROM(SELECT count(1)
                                       FROM crt_versionlog v
                                                INNER JOIN crt_reportdetails rd ON v.reportid=rd.reportid
                                       WHERE LastRunDate BETWEEN (DATE(_startDate)) AND (DATE_ADD(DATE(_endDate),INTERVAL 1 DAY))
                                       GROUP BY rd.reportid)a;

    ELSEIF _requestType="GetVersionByLogId" THEN

        SELECT ifnull(rd.LastRunDate,''), ifnull(rd.reportname,'') ReportName,ifnull(rd.reportdesc,'') ReportDesc, v.loggedon DateModified,v.site Site,
               rd.emailid EmailId,v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId,
               v.xml_value XML_Value,v.jsonquery JsonQuery,v.versionid Version FROM crt_versionlog v
                                                                                        LEFT JOIN crt_reportdetails rd
                                                                                                  ON v.reportId=rd.reportId
        WHERE v.loguuid=_logId;

    ELSEIF _requestType="GetAllJsonQueriesCount" THEN

        SELECT count(1) as counts FROM  crt_versionlog v
        WHERE v.logtype='GenJsonQuery' ;

    ELSEIF _requestType="GetAllJsonQueries" THEN

        SELECT * FROM (SELECT @row_num := @row_num + 1 as row_number, loguuid,loggedon DateCreated,site Site,stage Stage,
        contenttype ContentType, userid UserId
                       FROM cmsdq_reportingtool.crt_versionlog
                       WHERE logtype='GenJsonQuery' order by loggedon desc)a
        WHERE (row_number>=_startIndex and row_number<(_startIndex+_noofRows));


    ELSEIF _requestType="GetJsonQueryByLogId" THEN

        SELECT ifnull(rd.LastRunDate,''), ifnull(rd.reportname,'') ReportName,ifnull(rd.reportdesc,'') ReportDesc, v.loggedon DateModified,v.site Site,
               rd.emailid EmailId,v.stage Stage,v.contenttype ContentType,responsetime JsonResponseTime,v.userid UserId,
               v.xml_value XML_Value,v.jsonquery,v.versionid Version FROM crt_versionlog v
                                                                              LEFT JOIN crt_reportdetails rd
                                                                                        ON v.reportId=rd.reportId
        WHERE v.loguuid=_logId;

    END IF;

    END$$
DELIMITER ;


Stored Procedure

usp_crt_logversioninfo

DELIMITER $$
CREATE DEFINER=`arcrpt000plaglba`@`172.__.%` PROCEDURE `usp_crt_logversioninfo`(
    _requestType varchar(100),
    _loguuid char(36),
    _reportId char(36),
    _jpQuery longtext,
    _jpReposneTime varchar(50),
    _userId varchar(100),
    _userAction varchar(100),
    _ErrorDescription longtext,
    _xml_vale longtext
)
BEGIN
    DECLARE vVersionId int;


    IF _requestType="InsertVersion" THEN

        SELECT (ifnull(MAX(versionid),0)+1) INTO vVersionId FROM cmsdq_reportingtool.crt_versionlog WHERE reportid=_reportId;

        INSERT INTO cmsdq_reportingtool.crt_versionlog(loguuid, reportId, loggedon, site, stage, contenttype, responsetime, versionid, useraction, xml_value, jsonquery, logtype, userid, ErrorDescription)
        SELECT _loguuid,reportId, now(),extractvalue(xml_Value,'/SelectionCriteria/Filters/@Sites') as site,
               extractvalue(xml_Value,'/SelectionCriteria/Filters/@Stages') as stage,
               extractvalue(xml_Value,'/SelectionCriteria/Filters/@ContentTypesText') as contenttype,
               _jpReposneTime,vVersionId, _userAction, xml_value,_jpQuery,'GenReport', userId,_ErrorDescription
        FROM cmsdq_reportingtool.crt_reportdetails
        WHERE reportid=_reportId;

    ELSEIF _requestType="UpdateVersion" THEN

        UPDATE cmsdq_reportingtool.crt_versionlog SET  jsonquery=_jpQuery,responsetime=_jpReposneTime,ErrorDescription=_ErrorDescription
        WHERE loguuid=_loguuid;

    ELSEIF _requestType="InsertJsonVersion" THEN
        INSERT INTO cmsdq_reportingtool.crt_versionlog(loguuid, reportId, loggedon, site, stage, contenttype, responsetime, versionid, useraction, xml_value, jsonquery, logtype, userid, ErrorDescription)
        SELECT _loguuid,_reportId, now(),extractvalue(_xml_vale,'/SelectionCriteria/Filters/@Sites') as site,
               extractvalue(_xml_vale,'/SelectionCriteria/Filters/@Stages') as stage,
               extractvalue(_xml_vale,'/SelectionCriteria/Filters/@ContentTypesText') as contenttype,
               _jpReposneTime,0, _userAction, _xml_vale,_jpQuery,'GenJsonQuery', _userId,_ErrorDescription;
    END IF;

    END$$
DELIMITER ;

Stored Procedure

usp_crt_managereportdetails

DELIMITER $$
CREATE DEFINER=`arcrpt000plaglba`@`172.__.%` PROCEDURE `usp_crt_managereportdetails`(
    _ReportId varchar(45),
    _ReportName varchar(200) ,
    _ReportDesc varchar(500) ,
    _UserID varchar(50) ,
    _EmailID varchar(45),
    _Xml_Value longtext ,
    _Type varchar(45),
    _status varchar(1),
    _percentage varchar(4000)
)
    MODIFIES SQL DATA
    SQL SECURITY INVOKER
BEGIN

    DECLARE _lThreadLimit, _lCurrentThreads int;
    SET _lThreadLimit = 5;
    IF _Type = "UPSERT" THEN

        IF _ReportId=""  THEN

            SET _ReportId= uuid() ;

            INSERT INTO cmsdq_reportingtool.crt_reportdetails (ReportId,ReportName,ReportDesc,UserID,EmailID,Xml_Value)
            VALUES (_ReportId,_ReportName,_ReportDesc,_UserID,_EmailID,_Xml_Value);

        ELSE

            UPDATE cmsdq_reportingtool.crt_reportdetails
            SET ReportName=_ReportName,
                ReportDesc = _ReportDesc,
                EmailID    = _EmailID,
                Xml_Value  = _Xml_Value,
                LastRunDate=now()
            WHERE ReportId = _ReportId;

        END IF;
        SELECT _ReportId;

    ELSEIF _Type = "CLONE" THEN


        INSERT INTO cmsdq_reportingtool.crt_reportdetails (ReportId,ReportName,ReportDesc,UserID,EmailID,Xml_Value)
            (Select UUID(),CONCAT('Copy of ',ReportName),ReportDesc,UserID,EmailID,Xml_Value from  cmsdq_reportingtool.crt_reportdetails where ReportID = _ReportID);


    ELSEIF _Type = "DISABLE" THEN

        Update cmsdq_reportingtool.crt_reportdetails set status = 'D' , percentage = '',isrerun= 0
        WHERE ReportId = _ReportId;



    ELSEIF _Type = "MULTISELECT" THEN

        SELECT ReportId,ReportName,ReportDesc,UserID,EmailID, LastRunDate, status, ifnull(percentage,'') percentage, ifnull(updatedon,'') updatedon
        FROM cmsdq_reportingtool.crt_reportdetails
        WHERE UserID = _UserID order by case status when 'P' then 1 else 2 end, LastRunDate desc;


    ELSEIF _Type = "UNISELECT" THEN

        SELECT ReportId,ReportName,ReportDesc,UserID,EmailID,XML_Value,status, LastRunDate,errorcount
        FROM cmsdq_reportingtool.crt_reportdetails
        WHERE ReportId = _ReportId ;

    ELSEIF _Type = "DELETE" THEN

        DELETE FROM cmsdq_reportingtool.crt_reportdetails
        WHERE ReportId = _ReportId;

    ELSEIF _Type = "UPDATE_STATUS" THEN
        IF (_percentage ='0%') THEN
            UPDATE cmsdq_reportingtool.crt_reportdetails SET LastRunDate = now(),errorcount = 0
            WHERE ReportId = _ReportId and (ifnull(status,'') <> 'D' or (ifnull(status,'') ='D' and isrerun =1));
        END IF;
        UPDATE cmsdq_reportingtool.crt_reportdetails SET status = _status, percentage = _percentage, updatedon = now(),
                                                  errorcount = (case when _status ='E' then errorcount+1
                                                                     when _status ='C' then 0
                                                                     else errorcount end )
        WHERE ReportId = _ReportId and (ifnull(status,'') <> 'D' or (ifnull(status,'') ='D' and isrerun =1));


    ELSEIF _Type = "SELECT_PENDING" THEN

        SELECT count(ReportId) INTO _lCurrentThreads
        FROM cmsdq_reportingtool.crt_reportdetails
        WHERE (status = 'P' and percentage = '0%') or (status = 'E' and errorcount<4) or (status='P' and percentage = '80%' and time_to_sec(timediff(now(), updatedon )) / 3600 > 1) ;

        IF _lThreadLimit>= _lCurrentThreads THEN
            SELECT ReportId,status
            FROM cmsdq_reportingtool.crt_reportdetails
            WHERE (status = 'P' and percentage = '0%') or (status = 'E' and errorcount<4) or (status='P' and percentage = '80%' and time_to_sec(timediff(now(), updatedon )) / 3600 > 1)  order by LastRunDate asc limit _lThreadLimit ;
        ELSE

            SET _lThreadLimit = _lThreadLimit-_lCurrentThreads;

            -- IF _lThreadLimit =0 THEN

-- SET  _lThreadLimit = _lThreadLimit+1;

-- END IF

            SELECT ReportId,status
            FROM cmsdq_reportingtool.crt_reportdetails
            WHERE (status = 'P' and percentage = '0%') or (status = 'E' and errorcount<4) or (status='P' and percentage = '80%' and time_to_sec(timediff(now(), updatedon )) / 3600 > 1)  order by LastRunDate asc limit _lThreadLimit ;


        END IF;
    ELSEIF _Type = "SELECT_STATUS" THEN

        SELECT status
        FROM cmsdq_reportingtool.crt_reportdetails
        WHERE ReportId = _ReportId;

    ELSEIF _Type = "UPDATE_RERUN" THEN

        update cmsdq_reportingtool.crt_reportdetails set isrerun = 1
        WHERE ReportId = _ReportId;

    END IF;


    END$$
DELIMITER ;
