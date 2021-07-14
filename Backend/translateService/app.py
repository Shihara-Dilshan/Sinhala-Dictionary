from flask import Flask
from flask_cors import CORS
from api.api import ocr_route

app = Flask(__name__)
app.register_blueprint(ocr_route)
 
CORS(app)

if __name__ == "__main__":
    app.run(debug=True)