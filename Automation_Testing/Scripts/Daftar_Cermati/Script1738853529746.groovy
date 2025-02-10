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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.cermati.com/app/gabung')

WebUI.setText(findTestObject('Object Repository/Page_Daftar/input_No. Handphone_mobilePhone'), '081399793937')

WebUI.setText(findTestObject('Object Repository/Page_Daftar/input_Email_email'), 'deagmaryadi@gmail.com')

WebUI.setText(findTestObject('Object Repository/Page_Daftar/input_Nama Depan_firstName'), 'Dea')

WebUI.setText(findTestObject('Object Repository/Page_Daftar/input_Nama Belakang_lastName'), 'Maryadi')

WebUI.click(findTestObject('Object Repository/Page_Daftar/button_Daftar'))

WebUI.delay(10)

//call test case whatsapp, dikarenakan tidak ada langganan API whatsapp untuk automatically terhubung ke whatsapp, jadi ini melakukan pembacaan dari whatsapp

// 1. Buka WhatsApp Web dan melakukan connect whatsapp web
WebUI.executeJavaScript("window.open('https://web.whatsapp.com', '_blank');", [])
WebUI.delay(10) // Tunggu user scan QR Code

WebDriver driver = DriverFactory.getWebDriver()

try {
    //️Pastikan chat dengan pengirim OTP terlihat
    String senderName = "Cermati OTP" // Ganti dengan nama pengirim OTP yang sesuai
    WebElement chat = driver.findElement(By.xpath("//span[contains(text(),'Cermati OTP')]"))
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
	
	//tombol buat copy otpCode
	StringSelection selection = new StringSelection(otpCode)
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null)
	
	WebUI.setText(findTestObject('Object Repository/Input OTP'), otpCode)

} catch (Exception e) {
    println("Gagal menemukan elemen: " + e.getMessage())
}

WebUI.switchToWindowIndex(0) // Kembali ke tab pertama

//tempel kode
WebUI.click(findTestObject('Object Repository/Page_Verifikasi No. Handphone/Button Tempel Kode'))

WebUI.waitForElementClickable(findTestObject('Object Repository/Page_Verifikasi No. Handphone/button_Buat PIN'), 10)

WebUI.click(findTestObject('Object Repository/Page_Verifikasi No. Handphone/button_Buat PIN'))

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin_1'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin_1_2'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin_1_2_3'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin_1_2_3_4'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Buat PIN Baru/input_Masukkan 6 digit PIN baru Anda_newPin_1_2_3_4_5'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin_1'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin_1_2'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin_1_2_3'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin_1_2_3_4'), '1')

WebUI.setText(findTestObject('Object Repository/Page_Ulangi PIN Baru/input_Ulangi 6 digit PIN baru Anda_confirmNewPin_1_2_3_4_5'), '1')

WebUI.closeBrowser() 

