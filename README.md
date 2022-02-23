# datagovernance

3.	Proposed strategy for Data testing with definitions

Test Measures Objective	DQ Dimension Parameters	Definition For DQ Dimensions	Qualifying ETL Job
Completeness	Duplicate Value Checks	Values in Columns are Unique	
	Integrity Constraint Checks	Foreign /Primary Key is Maintained	
	Out Of Boundaries Value Checks	Data in the destination table are in specific range	
	Slow Changing Dim Checks	Data in the SCD column are compatible with specified type (1,2, or 3)	
	Record Count Validation Checks	Source and Target table record count should match	
	Null Value Checks	Translation of blank to null in DW.	
			
Consistency	Field Mapping Checking	Fields are mapped depend on logic mapping document specification	
	Measure Value Checking	Measure Values are correctly calculated	
			
Uniqueness	Duplicate Values Checks	Values in Columns are Unique	
	Integrity Constraints Checks	Primary / Foreign Keys are maintained	
			
Validity	Data Type Checking	The datatype in destination field is the same datatype in source	
	Field Length Checking	Field length in destination table is same as in Source table	
			
Timeliness	Data Freshness Checking	Data represent reality from the required timestamp	
			
Accuracy	Out Of Boundaries Checks	Data in the destination table are in specific range	
	Truncated Values Checking	Data in the destination table are same as in source 	

Implementation

In this step first of all, the references will be defined to the involved databases: source database, staging database (if exists) and DW. Secondly, the ETL logical mapping document and the metadata repository involved in this step. The Logic data mapping document contains the journey for each extracted filed from the source system till the final destination. The responsibility of creating this document is for the business analyst. This is usually an excel sheet, also called a crosswalk or Interface Design Document (IDD). These are a layout that analyzes the source, or legacy, systems and also describes the business rules and transformations. It contains the following fields: target table name, target column name table type, Slowly changing Dimension (SCD) type, source database, source table name, source column name, and transformation .

Conclusion

In this framework for  QA, automating ETL testing for data quality has been proposed. The proposed framework delivers a wide coverage for data quality testing by framing testing activities within a modular methodology that can be customized according to ETL specificities, business rules, and constraints. The proposed test routines associated to quality parameter does not satisfy the fulfillment of data quality completely, but it raises the level of confidence in the data warehouse with respect to this quality parameter. More test routines will be added to the proposed matrix in the future to increase the test coverage.
![image](https://user-images.githubusercontent.com/95757818/155400435-fefc7eb3-bb2a-4736-b579-845087e8c625.png)
