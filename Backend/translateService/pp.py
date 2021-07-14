from googletrans import Translator

translator = Translator()
translation = translator.translate("මම යනවා", dest='en')
print(translation.text)
