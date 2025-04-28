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

// Call Test Object
def requestForecast = findTestObject('Object Repository/weather_api')

// Send request
def responseForecast = WS.sendRequest(findTestObject('Object Repository/weather_api'))

// Check Status Code
WS.verifyResponseStatusCode(responseForecast, 200)

// Take response body as a string
def responseBody = responseForecast.getResponseBodyContent()

// Parse response to JSON
def jsonResponse = new JsonSlurper().parseText(responseBody)

//Check to variable date
def responseDate = jsonResponse.list.dt_txt

//Ensure total days = 5
assert responseDate.size() == 5 : "Total doesnt match with the actual: " + responseDate.size()

// Load JSON Schema from file
File schemaFile = new File('/Users/deagm/Katalon Studio/Testing_API/Include/scripts/groovy/schemas/weather.schema.json ')

def schemaContent = new JsonSlurper().parseText(schemaFile.text) 

// Convert to JSONObject
def schemaObject = new JSONObject(schemaContent)
def responseObject = new JSONObject(new JsonSlurper().parseText(responseBody))

// Load schema validator
Schema schema = SchemaLoader.load(schemaObject)

// Validate response terhadap schema
//schema.validate(responseObject)
try {
    schema.validate(responseObject) // Melakukan validasi schema
    println('JSON Schema Validation: SUCCESS') // Jika validasi berhasil
} catch (org.everit.json.schema.ValidationException e) {
    // Menangkap dan mencetak semua pesan error dari validasi
    println("Validation failed with these errors: " + e.getAllMessages())
}


println('JSON Schema Validation: SUCCESS')
