from utils.translate import translate

def translate_service(data):
    print(data)
    input_text = data['input']
    lang1 = data['lang1']
    lang2 = data['lang2']

    return translate(input_text, lang1, lang2)