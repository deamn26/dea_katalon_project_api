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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor

// 1. Buka WhatsApp Web
WebUI.openBrowser('https://web.whatsapp.com')
WebUI.delay(20) // Tunggu user scan QR Code

WebDriver driver = DriverFactory.getWebDriver()

try {
    //️Pastikan chat dengan pengirim OTP terlihat
    String senderName = "Dea" // Ganti dengan nama pengirim OTP yang sesuai
    WebElement chat = driver.findElement(By.xpath("//span[contains(text(),'Dea')]"))
	chat.click()
	WebUI.delay(2)

    //Scroll otomatis ke pesan terbaru
    JavascriptExecutor js = (JavascriptExecutor) driver
    js.executeScript("document.querySelector('div.copyable-area').scrollTop = document.querySelector('div.copyable-area').scrollHeight;")
    WebUI.delay(2) // Beri waktu WhatsApp untuk memuat chat

    //Ambil pesan terakhir dari pengirim OTP
    WebElement lastMessage = driver.findElement(By.xpath("(//div[contains(@class,'message-in')]//span[contains(@class,'selectable-text')])[last()]"))
    String otpMessage = lastMessage.getText()

    //Ekstrak hanya angka dari OTP
    String otpCode = otpMessage.replaceAll("\\D", "")
    println("OTP yang diambil: " + otpCode)

    //Masukkan OTP ke dalam input form
	WebUI.setText(findTestObject('Object Repository/Input OTP'), otpCode)

} catch (Exception e) {
    println("❌ Gagal menemukan elemen: " + e.getMessage())
}

WebUI.closeBrowser()