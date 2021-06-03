import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListEligibilityPurposeEnum, defaultValue } from 'app/shared/model/list-eligibility-purpose-enum.model';

export const ACTION_TYPES = {
  FETCH_LISTELIGIBILITYPURPOSEENUM_LIST: 'listEligibilityPurposeEnum/FETCH_LISTELIGIBILITYPURPOSEENUM_LIST',
  FETCH_LISTELIGIBILITYPURPOSEENUM: 'listEligibilityPurposeEnum/FETCH_LISTELIGIBILITYPURPOSEENUM',
  CREATE_LISTELIGIBILITYPURPOSEENUM: 'listEligibilityPurposeEnum/CREATE_LISTELIGIBILITYPURPOSEENUM',
  UPDATE_LISTELIGIBILITYPURPOSEENUM: 'listEligibilityPurposeEnum/UPDATE_LISTELIGIBILITYPURPOSEENUM',
  PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM: 'listEligibilityPurposeEnum/PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM',
  DELETE_LISTELIGIBILITYPURPOSEENUM: 'listEligibilityPurposeEnum/DELETE_LISTELIGIBILITYPURPOSEENUM',
  RESET: 'listEligibilityPurposeEnum/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListEligibilityPurposeEnum>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ListEligibilityPurposeEnumState = Readonly<typeof initialState>;

// Reducer

export default (state: ListEligibilityPurposeEnumState = initialState, action): ListEligibilityPurposeEnumState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTELIGIBILITYPURPOSEENUM):
    case REQUEST(ACTION_TYPES.UPDATE_LISTELIGIBILITYPURPOSEENUM):
    case REQUEST(ACTION_TYPES.DELETE_LISTELIGIBILITYPURPOSEENUM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM):
    case FAILURE(ACTION_TYPES.CREATE_LISTELIGIBILITYPURPOSEENUM):
    case FAILURE(ACTION_TYPES.UPDATE_LISTELIGIBILITYPURPOSEENUM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM):
    case FAILURE(ACTION_TYPES.DELETE_LISTELIGIBILITYPURPOSEENUM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTELIGIBILITYPURPOSEENUM):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTELIGIBILITYPURPOSEENUM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTELIGIBILITYPURPOSEENUM):
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

const apiUrl = 'api/list-eligibility-purpose-enums';

// Actions

export const getEntities: ICrudGetAllAction<IListEligibilityPurposeEnum> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM_LIST,
  payload: axios.get<IListEligibilityPurposeEnum>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IListEligibilityPurposeEnum> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTELIGIBILITYPURPOSEENUM,
    payload: axios.get<IListEligibilityPurposeEnum>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IListEligibilityPurposeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTELIGIBILITYPURPOSEENUM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListEligibilityPurposeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTELIGIBILITYPURPOSEENUM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IListEligibilityPurposeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LISTELIGIBILITYPURPOSEENUM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListEligibilityPurposeEnum> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTELIGIBILITYPURPOSEENUM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
