import pytest
from matching_algorithm import match_listings

@pytest.fixture
def search_list():
    data1 = {"campaign": True, "gameName": "Pathfinder",
             "environment": "Online", "day": "Mon",
             "startTime": "5:00 Pm", "endTime": "7:30 PM",
             "difficulty": "Intermediate", "role": "DPS",
             "userProfileId": 1}
    data2 = {"campaign": False, "gameName": "DnD",
             "environment": "Online", "day": "Fri",
             "startTime": "5:00 Pm", "endTime": "7:30 PM",
             "difficulty": "First Game", "role": "Healer",
             "userProfileId": 2}
    data3 = {"campaign": True, "gameName": "Pathfinder",
             "environment": "Online", "day": "Mon",
             "startTime": "5:00 Pm", "endTime": "7:30 PM",
             "difficulty": "Intermediate", "role": "DPS",
             "userProfileId": 3}
    return [data1, data2, data3]


def test_perfect_match(search_list):
    match_data = {"campaign": False, "gameName": "Pathfinder",
                  "environment": "Online", "day": "Mon",
                  "startTime": "5:00 Pm", "endTime": "7:30 PM",
                  "difficulty": "Intermediate", "role": "DPS",
                  "userProfileId": 1}
    match_scores = match_listings(search_list, match_data)
    assert match_scores[0][1] == 1.0
