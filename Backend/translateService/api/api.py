from flask import Blueprint, request
from services.api_service import translate_service
ocr_route = Blueprint('ocr_route', __name__)

@ocr_route.route("/api/v2/translate", methods=['POST'])
def translate_route():
    data = request.get_json()
    return translate_service(data)