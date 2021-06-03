import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInformationSequence, defaultValue } from 'app/shared/model/information-sequence.model';

export const ACTION_TYPES = {
  FETCH_INFORMATIONSEQUENCE_LIST: 'informationSequence/FETCH_INFORMATIONSEQUENCE_LIST',
  FETCH_INFORMATIONSEQUENCE: 'informationSequence/FETCH_INFORMATIONSEQUENCE',
  CREATE_INFORMATIONSEQUENCE: 'informationSequence/CREATE_INFORMATIONSEQUENCE',
  UPDATE_INFORMATIONSEQUENCE: 'informationSequence/UPDATE_INFORMATIONSEQUENCE',
  PARTIAL_UPDATE_INFORMATIONSEQUENCE: 'informationSequence/PARTIAL_UPDATE_INFORMATIONSEQUENCE',
  DELETE_INFORMATIONSEQUENCE: 'informationSequence/DELETE_INFORMATIONSEQUENCE',
  RESET: 'informationSequence/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInformationSequence>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type InformationSequenceState = Readonly<typeof initialState>;

// Reducer

export default (state: InformationSequenceState = initialState, action): InformationSequenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INFORMATIONSEQUENCE):
    case REQUEST(ACTION_TYPES.UPDATE_INFORMATIONSEQUENCE):
    case REQUEST(ACTION_TYPES.DELETE_INFORMATIONSEQUENCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_INFORMATIONSEQUENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE):
    case FAILURE(ACTION_TYPES.CREATE_INFORMATIONSEQUENCE):
    case FAILURE(ACTION_TYPES.UPDATE_INFORMATIONSEQUENCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_INFORMATIONSEQUENCE):
    case FAILURE(ACTION_TYPES.DELETE_INFORMATIONSEQUENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INFORMATIONSEQUENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INFORMATIONSEQUENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_INFORMATIONSEQUENCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_INFORMATIONSEQUENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INFORMATIONSEQUENCE):
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

const apiUrl = 'api/information-sequences';

// Actions

export const getEntities: ICrudGetAllAction<IInformationSequence> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INFORMATIONSEQUENCE_LIST,
  payload: axios.get<IInformationSequence>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IInformationSequence> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INFORMATIONSEQUENCE,
    payload: axios.get<IInformationSequence>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInformationSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INFORMATIONSEQUENCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInformationSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INFORMATIONSEQUENCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IInformationSequence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_INFORMATIONSEQUENCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInformationSequence> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INFORMATIONSEQUENCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
