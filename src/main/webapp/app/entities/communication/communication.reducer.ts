import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommunication, defaultValue } from 'app/shared/model/communication.model';

export const ACTION_TYPES = {
  FETCH_COMMUNICATION_LIST: 'communication/FETCH_COMMUNICATION_LIST',
  FETCH_COMMUNICATION: 'communication/FETCH_COMMUNICATION',
  CREATE_COMMUNICATION: 'communication/CREATE_COMMUNICATION',
  UPDATE_COMMUNICATION: 'communication/UPDATE_COMMUNICATION',
  PARTIAL_UPDATE_COMMUNICATION: 'communication/PARTIAL_UPDATE_COMMUNICATION',
  DELETE_COMMUNICATION: 'communication/DELETE_COMMUNICATION',
  RESET: 'communication/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommunication>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CommunicationState = Readonly<typeof initialState>;

// Reducer

export default (state: CommunicationState = initialState, action): CommunicationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMMUNICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMUNICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMUNICATION):
    case REQUEST(ACTION_TYPES.UPDATE_COMMUNICATION):
    case REQUEST(ACTION_TYPES.DELETE_COMMUNICATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMMUNICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMUNICATION):
    case FAILURE(ACTION_TYPES.CREATE_COMMUNICATION):
    case FAILURE(ACTION_TYPES.UPDATE_COMMUNICATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATION):
    case FAILURE(ACTION_TYPES.DELETE_COMMUNICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMUNICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMUNICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMUNICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMUNICATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMUNICATION):
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

const apiUrl = 'api/communications';

// Actions

export const getEntities: ICrudGetAllAction<ICommunication> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMMUNICATION_LIST,
  payload: axios.get<ICommunication>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICommunication> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMUNICATION,
    payload: axios.get<ICommunication>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICommunication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMUNICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommunication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMUNICATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICommunication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommunication> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMUNICATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
