from flask import Blueprint, request, make_response
from services.api_service import translate_service
ocr_route = Blueprint('ocr_route', __name__)

@ocr_route.route("/api/v2/translate", methods=['POST'])
def translate_route():
    data = request.get_json()
    result = translate_service(data)
    return make_response({"result": result})

@ocr_route.route("/api/v2/get", methods=['GET'])
def translate_rsoute():
    return "hi"
