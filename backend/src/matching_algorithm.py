import json
from listing import Listing
from datetime import datetime

def match_listings(data_set: list[str], to_match: str) -> list[tuple[str, float]]:
    my_listing = Listing(json.dumps(to_match))
    searches = [Listing(json.dumps(entry)) for entry in data_set]
    scores = []
    for item in searches:
        if not item.campaign ^ my_listing.campaign:
            scores.append(0.0)
            continue
        game_match = 1.0 if item.gameName == my_listing.gameName else 0.0
        role_match = (check_roles(item, my_listing) if item.campaign \
             else check_roles(my_listing, item)) * 0.1
        location_match = location_overlap(item, my_listing)
        level_match = 0.1 if item.difficulty == my_listing.difficulty else 0.0
        time_match = 0.0 if item.day != my_listing.day \
            else schedule_overlap(item, my_listing) * 0.6
        compatability = game_match * \
            (role_match + location_match + level_match + time_match)
        scores.append(compatability)
    if 1.0 in scores:
        matches = [(result, points * 100) for result, points \
                   in zip(data_set, scores) if points == 1.0]
    else:
        matches = [(result, points * 100) for result, points \
                   in zip(data_set, scores) if points >= 0.7]
    
    return sorted(matches, key=lambda x: x[1], reverse=True)

def check_roles(campaign: Listing, character: Listing) -> int:
    return character.role in campaign.role

def location_overlap(entry: Listing, being_matched: Listing) ->float:
    if entry.environment != being_matched.environment:
        return 0.0
    return 0.2

def schedule_overlap(entry: Listing, being_matched: Listing) ->float :
    time_format = '%H:%M %p'
    entry_start = datetime.strptime(entry.startTime, time_format)
    entry_end = datetime.strptime(entry.endTime, time_format)
    match_start = datetime.strptime(being_matched.startTime, time_format)
    match_end = datetime.strptime(being_matched.endTime, time_format)
    time_top = min(entry_end, match_end) - max(entry_start, match_start)
    time_bottom = (match_end - match_start) + (entry_end - entry_start)
    return 2.0 * (time_top / time_bottom)