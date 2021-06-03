import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEncounter, defaultValue } from 'app/shared/model/encounter.model';

export const ACTION_TYPES = {
  FETCH_ENCOUNTER_LIST: 'encounter/FETCH_ENCOUNTER_LIST',
  FETCH_ENCOUNTER: 'encounter/FETCH_ENCOUNTER',
  CREATE_ENCOUNTER: 'encounter/CREATE_ENCOUNTER',
  UPDATE_ENCOUNTER: 'encounter/UPDATE_ENCOUNTER',
  PARTIAL_UPDATE_ENCOUNTER: 'encounter/PARTIAL_UPDATE_ENCOUNTER',
  DELETE_ENCOUNTER: 'encounter/DELETE_ENCOUNTER',
  RESET: 'encounter/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEncounter>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EncounterState = Readonly<typeof initialState>;

// Reducer

export default (state: EncounterState = initialState, action): EncounterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENCOUNTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENCOUNTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ENCOUNTER):
    case REQUEST(ACTION_TYPES.UPDATE_ENCOUNTER):
    case REQUEST(ACTION_TYPES.DELETE_ENCOUNTER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ENCOUNTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ENCOUNTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENCOUNTER):
    case FAILURE(ACTION_TYPES.CREATE_ENCOUNTER):
    case FAILURE(ACTION_TYPES.UPDATE_ENCOUNTER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ENCOUNTER):
    case FAILURE(ACTION_TYPES.DELETE_ENCOUNTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENCOUNTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENCOUNTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENCOUNTER):
    case SUCCESS(ACTION_TYPES.UPDATE_ENCOUNTER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ENCOUNTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENCOUNTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/encounters';

// Actions

export const getEntities: ICrudGetAllAction<IEncounter> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ENCOUNTER_LIST,
  payload: axios.get<IEncounter>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEncounter> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENCOUNTER,
    payload: axios.get<IEncounter>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEncounter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENCOUNTER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEncounter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENCOUNTER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEncounter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ENCOUNTER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEncounter> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENCOUNTER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
