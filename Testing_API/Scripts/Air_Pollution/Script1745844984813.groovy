import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil
import java.io.File
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import static com.kms.katalon.core.util.KeywordUtil.markFailed
import static com.kms.katalon.core.util.KeywordUtil.markPassed
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import com.kms.katalon.core.testobject.ObjectRepository as OR
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.TestObject
import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.json.JSONTokener
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.Date

// Call Test Object
def requestForecast = findTestObject('Object Repository/pollution_api')

// Send request
def responseForecast = WS.sendRequest(findTestObject('Object Repository/pollution_api'))

// Check Status Code
WS.verifyResponseStatusCode(responseForecast, 200)

// Take response body as a string
def responseBody = responseForecast.getResponseBodyContent()

// Parse response to JSON
def jsonResponse = new JsonSlurper().parseText(responseBody)

//Check datetime to the exact time now
//Convert Unix timestamp to Date object
long unixTime = jsonResponse.list[0].dt

def date = new Date(unixTime * 1000)  // Multiply by 1000 because Unix timestamp is in seconds, but Java expects milliseconds

// Create a SimpleDateFormat object to format the date
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
sdf.setTimeZone(TimeZone.getTimeZone("UTC"))  // Set UTC time zone

// Format the date to a readable string
String formattedDate = sdf.format(date)

println("Formatted Date in UTC: " + formattedDate)

//Check date now to compare
long dateMillis = date.getTime()

println("Date.getTime() value: " + dateMillis)
println("Expected millis (unixTime * 1000): " + (unixTime * 1000))

// Cek sama atau tidak
if (dateMillis == unixTime * 1000) {
	println("Check PASSED: date.getTime() matches unixTime * 1000")
} else {
	println("Check FAILED: date.getTime() DOES NOT match unixTime * 1000")
}

// Load JSON Schema from file
File schemaFile = new File('/Users/deagm/Katalon Studio/Testing_API/Include/scripts/groovy/schemas/pollution.schema.json  ')

//def schemaContent = new JsonSlurper().parseText(JSONScheme.text)
def schemaContent = new JsonSlurper().parseText(schemaFile.text)

// Convert to JSONObject
def schemaObject = new JSONObject(schemaContent)
def responseObject = new JSONObject(new JsonSlurper().parseText(responseBody))

// Load schema validator
Schema schema = SchemaLoader.load(schemaObject)

// Validate response terhadap schema
try {
	schema.validate(responseObject) // Melakukan validasi schema
	println('JSON Schema Validation: SUCCESS') // Jika validasi berhasil
} catch (org.everit.json.schema.ValidationException e) {
	// Menangkap dan mencetak semua pesan error dari validasi
	println("Validation failed with these errors: " + e.getAllMessages())
}


println('JSON Schema Validation: SUCCESS')



