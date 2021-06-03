import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOpeOutErrorMessages, defaultValue } from 'app/shared/model/ope-out-error-messages.model';

export const ACTION_TYPES = {
  FETCH_OPEOUTERRORMESSAGES_LIST: 'opeOutErrorMessages/FETCH_OPEOUTERRORMESSAGES_LIST',
  FETCH_OPEOUTERRORMESSAGES: 'opeOutErrorMessages/FETCH_OPEOUTERRORMESSAGES',
  CREATE_OPEOUTERRORMESSAGES: 'opeOutErrorMessages/CREATE_OPEOUTERRORMESSAGES',
  UPDATE_OPEOUTERRORMESSAGES: 'opeOutErrorMessages/UPDATE_OPEOUTERRORMESSAGES',
  PARTIAL_UPDATE_OPEOUTERRORMESSAGES: 'opeOutErrorMessages/PARTIAL_UPDATE_OPEOUTERRORMESSAGES',
  DELETE_OPEOUTERRORMESSAGES: 'opeOutErrorMessages/DELETE_OPEOUTERRORMESSAGES',
  RESET: 'opeOutErrorMessages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOpeOutErrorMessages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OpeOutErrorMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: OpeOutErrorMessagesState = initialState, action): OpeOutErrorMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OPEOUTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_OPEOUTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_OPEOUTERRORMESSAGES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OPEOUTERRORMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_OPEOUTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_OPEOUTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OPEOUTERRORMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_OPEOUTERRORMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OPEOUTERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_OPEOUTERRORMESSAGES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OPEOUTERRORMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OPEOUTERRORMESSAGES):
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

const apiUrl = 'api/ope-out-error-messages';

// Actions

export const getEntities: ICrudGetAllAction<IOpeOutErrorMessages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES_LIST,
  payload: axios.get<IOpeOutErrorMessages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOpeOutErrorMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OPEOUTERRORMESSAGES,
    payload: axios.get<IOpeOutErrorMessages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOpeOutErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OPEOUTERRORMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOpeOutErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OPEOUTERRORMESSAGES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOpeOutErrorMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OPEOUTERRORMESSAGES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOpeOutErrorMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OPEOUTERRORMESSAGES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
