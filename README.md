# dea_katalon_project_api

Project Overview
This small project is designed to check two conditions:

5-day Weather Forecast

Current Air Pollution Data for South Jakarta

For the weather forecast, I first retrieve the latitude and longitude using the get_details object from the Object Repository. These values are then stored as variables and used in both Forecast_Weather.tc and Air_Pollution.tc.

I also performed validations on the response body by comparing the returned JSON with the expected schema. During testing, I found a bug in Air_Pollution.tc: the date-time format in the JSON schema didn’t match the actual API response. The schema expected the ISO 8601 format (2025-04-28T18:00:00Z), but the response returned a simpler format (yyyy-MM-dd HH:mm:ss). To fix this, I updated the schema validation to use a pattern instead of strict format matching.
