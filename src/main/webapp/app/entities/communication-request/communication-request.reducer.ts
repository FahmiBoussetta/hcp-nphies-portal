import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommunicationRequest, defaultValue } from 'app/shared/model/communication-request.model';

export const ACTION_TYPES = {
  FETCH_COMMUNICATIONREQUEST_LIST: 'communicationRequest/FETCH_COMMUNICATIONREQUEST_LIST',
  FETCH_COMMUNICATIONREQUEST: 'communicationRequest/FETCH_COMMUNICATIONREQUEST',
  CREATE_COMMUNICATIONREQUEST: 'communicationRequest/CREATE_COMMUNICATIONREQUEST',
  UPDATE_COMMUNICATIONREQUEST: 'communicationRequest/UPDATE_COMMUNICATIONREQUEST',
  PARTIAL_UPDATE_COMMUNICATIONREQUEST: 'communicationRequest/PARTIAL_UPDATE_COMMUNICATIONREQUEST',
  DELETE_COMMUNICATIONREQUEST: 'communicationRequest/DELETE_COMMUNICATIONREQUEST',
  RESET: 'communicationRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommunicationRequest>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CommunicationRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: CommunicationRequestState = initialState, action): CommunicationRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMUNICATIONREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_COMMUNICATIONREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_COMMUNICATIONREQUEST):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATIONREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_COMMUNICATIONREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_COMMUNICATIONREQUEST):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATIONREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_COMMUNICATIONREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMUNICATIONREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMUNICATIONREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMUNICATIONREQUEST):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATIONREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMUNICATIONREQUEST):
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

const apiUrl = 'api/communication-requests';

// Actions

export const getEntities: ICrudGetAllAction<ICommunicationRequest> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMMUNICATIONREQUEST_LIST,
  payload: axios.get<ICommunicationRequest>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICommunicationRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMUNICATIONREQUEST,
    payload: axios.get<ICommunicationRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICommunicationRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMUNICATIONREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommunicationRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMUNICATIONREQUEST,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICommunicationRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COMMUNICATIONREQUEST,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommunicationRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMUNICATIONREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
