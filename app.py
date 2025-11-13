from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import os
from datetime import datetime

app = Flask(__name__)
CORS(app)

DATA_FILE = 'recognitions.json'

def load_data():
    if os.path.exists(DATA_FILE):
        with open(DATA_FILE, 'r') as f:
            return json.load(f)
    return []

def save_data(data):
    with open(DATA_FILE, 'w') as f:
        json.dump(data, f, indent=2)

@app.route('/api/recognitions', methods=['GET'])
def get_recognitions():
    return jsonify(load_data())

@app.route('/api/recognitions', methods=['POST'])
def add_recognition():
    data = load_data()
    recognition = request.json
    recognition['date'] = datetime.now().isoformat()
    data.append(recognition)
    save_data(data)
    return jsonify({'success': True})

if __name__ == '__main__':
    app.run(debug=True, port=5000)